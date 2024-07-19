package com.example.myapplication.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.NetWorkData
import com.google.gson.Gson
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bouncycastle.asn1.iana.IANAObjectIdentifiers.directory
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = JsonReader.readJsonFromAssets(this, "file.json")

        data?.routers?.forEach {
            Log.d("MyData", " ${it.routerName}")
        }

        val recyclerView: RecyclerView = findViewById(R.id.mainRecyclerView)
        val bMImageView: ImageView = findViewById(R.id.bitmapImageView)
        val showBn: Button = findViewById(R.id.showBn)
        recyclerView.layoutManager = LinearLayoutManager(this)

        data?.routers.let {
            if (it != null) {
                val adapter = RouterAdapter2(it)
                recyclerView.adapter = adapter
            }


//            it.routers.forEach{
//
//            }
        }



        showBn.setOnClickListener {
            val recyclerViewBitmap = recyclerView.toBitmap()
            bMImageView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            bMImageView.setImageBitmap(recyclerViewBitmap)
            val imagePath = saveBitmapToInternalStorage(this,recyclerViewBitmap,)
            storeToPDF(imagePath)

        }


    }

    fun RecyclerView.toBitmap(): Bitmap {
        val adapter = adapter ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val itemCount = adapter.itemCount
        val layoutManager = layoutManager ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        var totalHeight = 0
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        var heightMeasureSpec: Int

        // Measure the height of all items
        for (i in 0 until itemCount) {
            val viewHolder = adapter.createViewHolder(this, adapter.getItemViewType(i))
            adapter.onBindViewHolder(viewHolder, i)
            viewHolder.itemView.measure(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED)
            totalHeight += viewHolder.itemView.measuredHeight
        }

        // Create a bitmap with the calculated height
        val bitmap = Bitmap.createBitmap(measuredWidth, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw each item on the canvas
        var top = 0
        for (i in 0 until itemCount) {
            val viewHolder = adapter.createViewHolder(this, adapter.getItemViewType(i))
            adapter.onBindViewHolder(viewHolder, i)
            viewHolder.itemView.measure(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED)
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(viewHolder.itemView.measuredHeight, View.MeasureSpec.EXACTLY)
            viewHolder.itemView.layout(0, 0, viewHolder.itemView.measuredWidth, viewHolder.itemView.measuredHeight)
            viewHolder.itemView.draw(canvas)
            canvas.translate(0f, viewHolder.itemView.height.toFloat())
            top += viewHolder.itemView.measuredHeight
        }

        return bitmap
    }

    private fun getBitmapFromView(view: ScrollView): Bitmap {
        val child = view.getChildAt(0)
        val width = child.width
        val height = child.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        child.draw(canvas)

        return bitmap
    }

    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap,): String {
        val fileName = "my_image"
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        if (dir != null && !dir.exists()) {
            dir.mkdirs() // Create the directory if it doesn't exist
        }
        val file = File(dir, "$fileName.png")
        var fileOutputStream: FileOutputStream? = null

        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
//        openImageFile(this,file.absolutePath)
        return file.absolutePath
    }

    fun openImageFile(context: Context, filePath: String) {
        Log.d("check", "check the image file path $filePath")
        val file = File(filePath)
        val uri: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "image/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }

    private fun storeToPDF(recyclerViewBitmap: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            // download to phone
//            val downloadsDir =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            if (!downloadsDir.exists()) {
//                downloadsDir.mkdirs()
//            }
            // write on internal storage
            val dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            if (dir != null && !dir.exists()) {
                dir.mkdirs() // Create the directory if it doesn't exist
            }

            val filePath = File(dir, "example.pdf").absolutePath

            val document = Document(PageSize.A4)
            val writer = PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()

            // Create a table with one column
            val table = PdfPTable(1)
            table.widthPercentage = 100f

            // Add Header
            val header = Paragraph("Timmy Jones", Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD))
            header.alignment = Element.ALIGN_LEFT
            table.addCell(createCell(header))

            // Add Subheader
            val subHeader = Paragraph(
                "Installation:ONT & RG",
                Font(Font.FontFamily.HELVETICA, 14f, Font.NORMAL)
            )
            subHeader.alignment = Element.ALIGN_LEFT
            table.addCell(createCell(subHeader))

            // Add Description
            val description =
                Paragraph("SERVICE INFORMATION", Font(Font.FontFamily.HELVETICA, 12f))
            description.alignment = Element.ALIGN_LEFT
            val descriptionCell = createCell(description, BaseColor(255, 0, 0, 7))
            descriptionCell.border = PdfPCell.BOTTOM
            descriptionCell.paddingBottom = 10f
            descriptionCell.borderColorBottom = BaseColor.GREEN
            descriptionCell.borderWidth = 1f
            table.addCell(descriptionCell)

            val stream = ByteArrayOutputStream()
            recyclerViewBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val image = Image.getInstance(byteArray)
            val pageWidth = document.pageSize.width - document.leftMargin() - document.rightMargin()
            val pageHeight = document.pageSize.height - document.topMargin() - document.bottomMargin()

            if (image.width > pageWidth || image.height > pageHeight) {
                image.scaleToFit(pageWidth, pageHeight)
            }
            document.add(image)
            document.add(table)
            document.close()

            MediaScannerConnection.scanFile(this@MainActivity, arrayOf(filePath), arrayOf("application/pdf"), null)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "PDF created at: $filePath", Toast.LENGTH_LONG)
                    .show()
            }
            openPdf(filePath)

        }
        }

    private fun storeToPDF(imagePath:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            if (dir != null && !dir.exists()) {
                dir.mkdirs() // Create the directory if it doesn't exist
            }
            val filePath = File(dir, "example.pdf").absolutePath

            val document = Document(PageSize.A4)
            val writer = PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()

            // Create a table with one column
            val table = PdfPTable(1)
            table.widthPercentage = 100f

            // Add Header
            val header = Paragraph("Timmy Jones", Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD))
            header.alignment = Element.ALIGN_LEFT
            table.addCell(createCell(header))

            // Add Subheader
            val subHeader = Paragraph(
                "Installation:ONT & RG",
                Font(Font.FontFamily.HELVETICA, 14f, Font.NORMAL)
            )
            subHeader.alignment = Element.ALIGN_LEFT
            table.addCell(createCell(subHeader))

            // Add Description
            val description =
                Paragraph("SERVICE INFORMATION", Font(Font.FontFamily.HELVETICA, 12f))
            description.alignment = Element.ALIGN_LEFT
            val descriptionCell = createCell(description, BaseColor(255, 0, 0, 7))
            descriptionCell.border = PdfPCell.BOTTOM
            descriptionCell.paddingBottom = 10f
            descriptionCell.borderColorBottom = BaseColor.GREEN
            descriptionCell.borderWidth = 1f
            table.addCell(descriptionCell)

            val image = Image.getInstance(imagePath)
            image.scaleToFit(500f, 500f) // Scale the image to fit the page
            document.add(image)
            document.add(table)
            document.close()

            MediaScannerConnection.scanFile(this@MainActivity, arrayOf(filePath), arrayOf("application/pdf"), null)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "PDF created at: $filePath", Toast.LENGTH_LONG)
                    .show()
            }
            openPdf(filePath)

        }
    }

    private fun openPdf(filePath: String) {
        val pdfFile = File(filePath)
        val pdfUri: Uri = FileProvider.getUriForFile(
            this,
            "$packageName.fileprovider",
            pdfFile
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    }

    private fun createCell(content: Element, backgroundColor: BaseColor? = null): PdfPCell {
        val cell = PdfPCell()
        cell.addElement(content)
        backgroundColor?.let {
            cell.backgroundColor = backgroundColor
        }
        if (backgroundColor == null)
            cell.border = PdfPCell.NO_BORDER
        return cell
    }

}


object JsonReader {

    fun readJsonFromAssets(context: Context, fileName: String): NetWorkData? {
        val gson = Gson()
        var json: String? = null
        try {
            // Read the JSON file from the assets folder
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            json = stringBuilder.toString()
            bufferedReader.close()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        // Parse JSON using Gson into a list of MyData objects
        return gson.fromJson(json, NetWorkData::class.java)
    }
}




