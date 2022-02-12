package com.project.generatepdf

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Color
import android.text.style.DynamicDrawableSpan.ALIGN_CENTER
import com.itextpdf.text.Element.ALIGN_CENTER
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory.COURIER
import com.itextpdf.text.pdf.BaseFont.COURIER
import com.itextpdf.text.pdf.PdfContentByte.ALIGN_CENTER
import com.itextpdf.text.pdf.PdfName.COURIER


class MainActivity : AppCompatActivity() {


    private lateinit var et_pdf_name : EditText
    private lateinit var et_pdf_address : EditText
    private lateinit var et_pdf_contact : EditText
    private lateinit var et_pdf_transactionNumber : EditText
    private lateinit var et_pdf_sub_total : EditText
    private lateinit var et_pdf_grand_total : EditText


    private lateinit var btn_generate_pdf : Button
    private val STORAGE_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        et_pdf_name = findViewById(R.id.et_pdf_name)
        et_pdf_address = findViewById(R.id.et_pdf_address)
        et_pdf_contact = findViewById(R.id.et_pdf_contact)
        et_pdf_transactionNumber = findViewById(R.id.et_pdf_transactionNumber)
        et_pdf_sub_total = findViewById(R.id.et_pdf_sub_total)
        et_pdf_grand_total = findViewById(R.id.et_pdf_grand_total)

        btn_generate_pdf = findViewById(R.id.btn_generate_pdf)
        btn_generate_pdf.setOnClickListener{
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ){
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                }else{
                    savePDF()
                }
            }else{
                savePDF()
            }
        }

    }
    private fun savePDF(){
        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyMMdd_HHmmss",Locale.getDefault())
            .format(System.currentTimeMillis())

        /*This method saved in:
         *
         * /storage/emulated/0/20220127_224414.pdf*/
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName +".pdf"
        Log.i("MAIN: ","${mFilePath}")
        try {

            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()



            val todayDate = SimpleDateFormat("MMMM dd, yyyy - HH-mm-ss",Locale.getDefault())
                .format(System.currentTimeMillis())

            val name = et_pdf_name.text.toString().trim()
            val address = et_pdf_address.text.toString().trim()
            val contact = et_pdf_contact.text.toString().trim()
            val transactionNumber = et_pdf_transactionNumber.text.toString().trim()
             val subtotal = et_pdf_sub_total.text.toString().trim()
             val grandtotal = et_pdf_grand_total.text.toString().trim()

            mDoc.addAuthor("john and lui code")
            mDoc.add(Paragraph("------------------------- 29-23 Avenida Rizal, Bacoor, Cavite -------------------------"))
            mDoc.add(Paragraph("                                                               Date: $todayDate"))
            mDoc.add(Paragraph("                                                               Branch: Cavite"))
            mDoc.add(Paragraph("                                                               Transaction Number: $transactionNumber"))
            mDoc.add(Paragraph("------------------------------------------------------------------------------------------------------"))
            mDoc.add(Paragraph("Customer Name: "+" ${name}"))
            mDoc.add(Paragraph("Customer Address: "+" ${address}"))
            mDoc.add(Paragraph("Mobile/Landline: "+" ${contact}" +" \n\n"))
            mDoc.add(Paragraph("------------------------- OEM Plus Car Parts and Accessories  -------------------------"))

            //* Todo Add List item
            mDoc.add(Paragraph("Item Name:" +" \n Raider Headlight" + "\n Motor Break" + "\n Side Mirror" + "\n Gear" + " \n\n\n\n\n\n\n"))
            //* Todo Add List item


            mDoc.add(Paragraph("                                                                       Sub Total:   " +" ${subtotal}"))
            mDoc.add(Paragraph("                                                                       Grand Total: " +" ${grandtotal}"))
            mDoc.add(Paragraph(" \n\n\n\n\n\n"))
            mDoc.add(Paragraph("------------------------- Thank you for doing business with us -------------------------"))

            mDoc.close()
            Toast.makeText(this,"$mFileName.pdf\n is create to\n$mFilePath", Toast.LENGTH_SHORT).show()

        }catch (e: Exception){
            Toast.makeText(this,""+e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            STORAGE_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else {
                    Toast.makeText(this,"permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}