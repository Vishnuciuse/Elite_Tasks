package com.example.myapplication.task4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.text.BaseColor
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
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class MainActivity5 : AppCompatActivity() {


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if ((permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true ||
                    permissions[Manifest.permission.MANAGE_EXTERNAL_STORAGE] == true) &&
            permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true
        ) {
            createPdf()
        } else {
            Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            if (checkPermissions()) {
                createPdf()

            } else {
                requestPermissions()
            }
        }

    }

    private fun openPdf(filePath: String) {
        val pdfFile = File(filePath)
        val pdfUri: Uri = FileProvider.getUriForFile(
            this,
            "$packageName.provider",
            pdfFile
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    }


    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val writePermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val readPermission = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            )
            writePermission == PackageManager.PERMISSION_GRANTED &&
                    readPermission == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun createPdf() {
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

            val subHeader1 = Paragraph(
                "Date",
                Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, BaseColor.GRAY)
            )
            val descriptionCell1 = createCell(subHeader1)
            descriptionCell1.paddingBottom = -5f
            table.addCell(descriptionCell1)

            val subHeader2 = Paragraph(
                "September 7, 2023",
                Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)
            )
            table.addCell(createCell(subHeader2))

            val subHeader3 = Paragraph(
                "Subscriber Name",
                Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, BaseColor.GRAY)
            )
            val descriptionCell2 = createCell(subHeader3)
            descriptionCell1.paddingBottom = -5f
            table.addCell(descriptionCell2)

            val subHeader4 = Paragraph(
                "Joe Moe",
                Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)
            )
            table.addCell(createCell(subHeader4))

            val subHeader10 = Paragraph(
                "Work Order",
                Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, BaseColor.GRAY)
            )
            val descriptionCell10 = createCell(subHeader10)
            descriptionCell1.paddingBottom = -5f
            table.addCell(descriptionCell10)

            val subHeader11 = Paragraph(
                "#000123",
                Font(Font.FontFamily.HELVETICA, 12f, Font.NORMAL)
            )
            table.addCell(createCell(subHeader11))

            val description12 =
                Paragraph("SUBSCRIBER PERMISSIONS", Font(Font.FontFamily.HELVETICA, 12f))
            description.alignment = Element.ALIGN_LEFT
            val descriptionCell12 = createCell(description12, BaseColor(255, 0, 0, 7))
            descriptionCell12.border = PdfPCell.BOTTOM
            descriptionCell12.paddingBottom = 10f
            descriptionCell12.borderColorBottom = BaseColor.GREEN
            descriptionCell12.borderWidth = 1f
            table.addCell(descriptionCell12)

            val table2 = PdfPTable(2)
            table2.widthPercentage = 100f
            // Add an Image from URL
            val imageUrl =
                URL("https://images.pexels.com/photos/6710897/pexels-photo-6710897.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2") // Replace with the actual URL
            val urlBitmap = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(imageUrl.openStream())
            }
//        val urlBitmap = BitmapFactory.decodeStream(imageUrl.openStream())
            val urlStream = ByteArrayOutputStream()
            urlBitmap.compress(Bitmap.CompressFormat.JPEG, 100, urlStream)
            val imageFromUrl = Image.getInstance(urlStream.toByteArray())
            imageFromUrl.scaleToFit(200f, 200f) // Set the width and height as needed
            imageFromUrl.alignment = Element.ALIGN_CENTER
            table2.addCell(createCell(imageFromUrl))

            val imageUrl1 =
                URL("https://images.pexels.com/photos/6710902/pexels-photo-6710902.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2") // Replace with the actual URL
            val urlBitmap1 = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(imageUrl1.openStream())
            }
            val urlStream1 = ByteArrayOutputStream()
            urlBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, urlStream1)
            val imageFromUrl1 = Image.getInstance(urlStream1.toByteArray())
            imageFromUrl1.scaleToFit(200f, 200f) // Set the width and height as needed
            imageFromUrl1.alignment = Element.ALIGN_CENTER
            table2.addCell(createCell(imageFromUrl1))

            val imageUrl2 =
                URL("https://images.pexels.com/photos/6710900/pexels-photo-6710900.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2") // Replace with the actual URL
            val urlBitmap2 = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(imageUrl2.openStream())
            }
            val urlStream2 = ByteArrayOutputStream()
            urlBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, urlStream2)
            val imageFromUrl2 = Image.getInstance(urlStream2.toByteArray())
            imageFromUrl2.scaleToFit(200f, 200f) // Set the width and height as needed
            imageFromUrl2.alignment = Element.ALIGN_CENTER
            table2.addCell(createCell(imageFromUrl2))

            val imageUrl3 =
                URL("https://images.pexels.com/photos/6710910/pexels-photo-6710910.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2") // Replace with the actual URL
            val urlBitmap3 = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(imageUrl3.openStream())
            }
            val urlStream3 = ByteArrayOutputStream()
            urlBitmap3.compress(Bitmap.CompressFormat.JPEG, 100, urlStream3)
            val imageFromUrl3 = Image.getInstance(urlStream3.toByteArray())
            imageFromUrl3.scaleToFit(200f, 200f) // Set the width and height as needed
            imageFromUrl3.alignment = Element.ALIGN_CENTER
            table2.addCell(createCell(imageFromUrl3))
//            table.addCell(createMultiImageCell(imageFromUrl,imageFromUrl1))

            // Add the table to the document
            document.add(table)
            document.add(table2)

            // Close the document
            document.close()

            // Notify the media scanner about the new file
            MediaScannerConnection.scanFile(this@MainActivity5, arrayOf(filePath), arrayOf("application/pdf"), null)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity5, "PDF created at: $filePath", Toast.LENGTH_LONG)
                    .show()
            }
            openPdf(filePath)
        }
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

    private fun createMultiImageCell(content: Element,content1: Element, backgroundColor: BaseColor? = null): PdfPCell {
        val cell = PdfPCell()
        cell.addElement(content)
        cell.addElement(content1)
        backgroundColor?.let {
            cell.backgroundColor = backgroundColor
        }
        if (backgroundColor == null)
            cell.border = PdfPCell.NO_BORDER

        /*CoroutineScope(Dispatchers.IO).launch {
            val imageUrl = arrayListOf<URL>()
                imageUrl.add(URL("https://marketplace.canva.com/EAFYecj_1Sc/1/0/1600w/canva-cream-and-black-simple-elegant-catering-food-logo-2LPev1tJbrg.jpg")) // Replace with the actual URL
                imageUrl.add(URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFsPoZ5DAtC-vAsNKl8Nj86WkbAH1kDiLpdY79UD-6Ww&s"))
                imageUrl.add(URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkMyMDtu1rPGjyU9BGJLcg7Tx5AnmorUMSU5iM6YRZPQ&s"))
//                imageUrl.add(URL("https://hips.hearstapps.com/hmg-prod/images/nature-quotes-landscape-1648265299.jpg?crop=0.676xw:1.00xh;0.148xw,0&resize=640:*"))

            for (i in imageUrl){
                val urlBitmap = withContext(Dispatchers.IO) {
                    BitmapFactory.decodeStream(i.openStream())
                }
//        val urlBitmap = BitmapFactory.decodeStream(imageUrl.openStream())
                val urlStream = ByteArrayOutputStream()
                urlBitmap.compress(Bitmap.CompressFormat.JPEG, 100, urlStream)
                val imageFromUrl = Image.getInstance(urlStream.toByteArray())
                imageFromUrl.scaleToFit(200f, 200f)

                cell.addElement(imageFromUrl)

                cell.border = PdfPCell.NO_BORDER
            }
        }*/
        return cell
    }

}