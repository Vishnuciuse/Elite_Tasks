package com.example.myapplication.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.NetWorkData
import com.google.gson.Gson
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            val bitmapList = listOfBitmap(recyclerViewBitmap)
            bMImageView.setImageBitmap(recyclerViewBitmap)
//            val imagePath = saveBitmapToInternalStorage(this,recyclerViewBitmap,)
            storeToPDF(recyclerViewBitmap)
//            saveBitmapToInternalStorage(this,recyclerViewBitmap)

        }

    }

    private fun listOfBitmap(recyclerViewBitmap: Bitmap): List<Bitmap>  {
         val bitmapList = mutableListOf<Bitmap>()
        // Get the dimensions of the original bitmap
        val width = recyclerViewBitmap.width
        val height = recyclerViewBitmap.height

        // Calculate the width of the split bitmaps
        val halfHeight = height / 2

        // Create the first half bitmap
        val bitmap1 = Bitmap.createBitmap(recyclerViewBitmap, 0, 0, width, halfHeight)
        val bitmap2 = Bitmap.createBitmap(recyclerViewBitmap, 0, halfHeight, width, halfHeight)
        bitmapList.add(bitmap1)
        bitmapList.add(bitmap2)
        return bitmapList
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

    fun RecyclerView.toBitmaps(): List<Bitmap> {
        val adapter = adapter ?: return listOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
        val itemCount = adapter.itemCount
        val layoutManager = layoutManager ?: return listOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))

        val bitmaps = mutableListOf<Bitmap>()
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        var heightMeasureSpec: Int

        // Create a list to store the height of each item
        val itemHeights = IntArray(itemCount)
        var totalHeight = 0

        // Measure the height of all items
        for (i in 0 until itemCount) {
            val viewHolder = adapter.createViewHolder(this, adapter.getItemViewType(i))
            adapter.onBindViewHolder(viewHolder, i)
            viewHolder.itemView.measure(widthMeasureSpec, View.MeasureSpec.UNSPECIFIED)
            itemHeights[i] = viewHolder.itemView.measuredHeight
            totalHeight += itemHeights[i]
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

            // Extract the bitmap of the individual item
            val itemBitmap = Bitmap.createBitmap(bitmap, 0, top - viewHolder.itemView.measuredHeight, measuredWidth, viewHolder.itemView.measuredHeight)
            bitmaps.add(itemBitmap)
        }

        return bitmaps
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
            // Create a new document with the same dimensions as the image
            val stream = ByteArrayOutputStream()
            recyclerViewBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()

            // Load the image from byte array
            val image = Image.getInstance(byteArray)
//            image.isScaleToFitLineWhenOverflow

            // Get image dimensions
            val imageWidth = image.plainWidth
            val imageHeight = image.plainHeight

            // Create a new document with the same dimensions as the image
            val pageSize = Rectangle(imageWidth, imageHeight)
            val document = Document(pageSize)
//            val document = Document(PageSize.A4)
            val writer = PdfWriter.getInstance(document, FileOutputStream(filePath))
            document.open()

            // Create a table with one column
            val table = PdfPTable(1)
            table.widthPercentage = 100f

            // Add Header
//            val header = Paragraph("Timmy Jones", Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD))
//            header.alignment = Element.ALIGN_LEFT
//            table.addCell(createCell(header))
//
//            // Add Subheader
//            val subHeader = Paragraph(
//                "Installation:ONT & RG",
//                Font(Font.FontFamily.HELVETICA, 14f, Font.NORMAL)
//            )
//            subHeader.alignment = Element.ALIGN_LEFT
//            table.addCell(createCell(subHeader))

            // Add Description
//            val description =
//                Paragraph("SERVICE INFORMATION", Font(Font.FontFamily.HELVETICA, 12f))
//            description.alignment = Element.ALIGN_LEFT
//            val descriptionCell = createCell(description, BaseColor(255, 0, 0, 7))
//            descriptionCell.border = PdfPCell.BOTTOM
//            descriptionCell.paddingBottom = 10f
//            descriptionCell.borderColorBottom = BaseColor.GREEN
//            descriptionCell.borderWidth = 1f
//            table.addCell(descriptionCell)

             // without convert to image , bitmap directly set to pdf
//            val stream = ByteArrayOutputStream()
//            recyclerViewBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray = stream.toByteArray()
//            val image = Image.getInstance(byteArray)
//            val pageWidth = document.pageSize.width - document.leftMargin() - document.rightMargin()
//            val pageHeight = document.pageSize.height - document.topMargin() - document.bottomMargin()
//
//            if (image.width > pageWidth || image.height > pageHeight) {
//                image.scaleToFit(pageWidth, pageHeight)
//            }
            document.add(image)
            val spaceBeforeImage = Paragraph()
            spaceBeforeImage.add(Chunk.NEWLINE) // Add a newline for space
            spaceBeforeImage.add(Chunk.NEWLINE) // Add a newline for space
            spaceBeforeImage.add(Chunk.NEWLINE) // Add a newline for space

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

    private fun storeToPDF(recyclerViewBitmap: List<Bitmap>) {
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

//            val image = Image.getInstance(imagePath)
//            image.scaleToFit(500f, 500f) // Scale the image to fit the page
//            document.add(image)

            for (element in recyclerViewBitmap){
                val stream = ByteArrayOutputStream()
                element.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val image = Image.getInstance(byteArray)
                val pageWidth = document.pageSize.width - document.leftMargin() - document.rightMargin()
                val pageHeight = document.pageSize.height - document.topMargin() - document.bottomMargin()

                if (image.width > pageWidth || image.height > pageHeight) {
                    image.scaleToFit(pageWidth, pageHeight)
                }
                document.add(image)
            }



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




