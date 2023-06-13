package com.example.rtepandroid

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.rtepandroid.databinding.ActivityUsersBinding
import com.example.rtepandroid.models.Department
import com.example.rtepandroid.models.Employee
import com.example.rtepandroid.retrofit.ApiEndPoints
import com.example.rtepandroid.retrofit.ServiceBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Calendar
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding
    val gson = GsonBuilder().create()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_users)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        set UI
        val departs = arrayOf("ICT", "Health", "Education", "Youths")
        val arrayDeparts = ArrayAdapter(this, R.layout.simple_spinner_item, departs)
        binding.psDepartment.adapter = arrayDeparts

        val genders = arrayOf("Male", "Female")
        val arrayGender = ArrayAdapter(this, R.layout.simple_spinner_item, genders)
        binding.psGender.adapter = arrayGender

        val role = arrayOf("User", "Admin", "Officer")
        val arrayRole = ArrayAdapter(this, R.layout.simple_spinner_item, role)
        binding.psRole.adapter = arrayRole

        binding.psEnterDOB.setOnClickListener {
            showDatePicker(binding.psEnterDOB, this@UsersActivity)
        }

        binding.psEnterDOH.setOnClickListener {
            showDatePicker(binding.psEnterDOH, this@UsersActivity)
        }

        fetchAllDepartments()

        initializeApp(this@UsersActivity)
        login("12345678", "daviekim")
//
//        createEmployee(
//            publicServiceNo = 1234567,
//            firstName = "john",
//            lastName = "kameno",
//            mobileNo = "90786542",
//            homeAddress = "Eldoret main stage",
//            email = "kameno.gmail.com",
//            designation = "alaa",
//            employmentType = "try this",
//            gender = "male",
//            role= "male",
//            nationalId = 7823456,
//            dateOfBirth = "202-06-08",
//            dateOfHire = "2023-06-08",
//            department = 1
//        )

        binding.psSubmit.setOnClickListener {
            val valid: Boolean = checkValidity()
            if (valid) createEmployee(
                publicServiceNo = binding.psPServiceNumber.toString().toInt(),
                firstName = binding.psEnterFirstname.text.toString(),
                lastName = binding.psEnterLastname.text.toString(),
                mobileNo = binding.psEnterMobileNo.text.toString(),
                homeAddress = binding.psEnterHomeAddress.text.toString(),
                email = binding.psEnterEmail.text.toString(),
                designation = binding.psEnterDesignation.text.toString(),
                employmentType = binding.psEnterEmpType.text.toString(),
                gender = binding.psGender.selectedItem.toString(),
                role = binding.psRole.selectedItem.toString(),
                nationalId = binding.psEnterID.text.toString().toInt(),
                dateOfBirth = binding.psEnterDOB.text.toString(),
                dateOfHire = binding.psEnterDOH.text.toString(),
                department = 1
            )

        }
    }

    private fun checkValidity(): Boolean {

        return true
    }

    fun showDatePicker(tv: Button, context: Context){
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                tv.text = "${year}-${monthOfYear}-${dayOfMonth}"
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    fun login(username: String, password: String) {
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg2MzAyODM5LCJpYXQiOjE2ODYzMDI1MzksImp0aSI6ImE2ZGRlMDVmODM5ZjQzZmZiZmFhODA2ZGQ2M2I3MmM3IiwidXNlcl9pZCI6MX0.bH6f3YmlrXYuE5MQfB-QAkOs-MuBI1tVLYDqR4eTIF0"
        ServiceBuilder.saveAuthToken(token)
    }

    fun logout() {
        ServiceBuilder.clearAuthToken()
    }

    fun initializeApp(context: Context) {
        ServiceBuilder.init(context)
    }

    private fun fetchAllDepartments(){
        var ids: ArrayList<Int> = ArrayList()
        var items = emptyList<Any>()
        Log.w("fetched departments", "working here")
        val request = ServiceBuilder.buildService(ApiEndPoints::class.java)
        val callFetchDepartments = request.getDepartments()

        callFetchDepartments.enqueue(object : retrofit2.Callback<List<Department>> {
            override fun onResponse(
                call: Call<List<Department>>,
                response: Response<List<Department>>
            ) {
                if (response.isSuccessful){
                    val departments = response.body()!!
//                    ids.add(response.body()!!.)
//                    binding.psDepartment.adapter =
                    Log.e("fetched departments", departments.toString())


                }
            }

            override fun onFailure(call: Call<List<Department>>, t: Throwable) {
                Log.e("error fetching departments", t.toString())
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createEmployee(
        publicServiceNo: Int,
        firstName: String,
        lastName: String,
        mobileNo: String,
        homeAddress: String,
        email: String,
        designation: String,
        employmentType: String,
        gender: String,
        role: String,
        nationalId: Int,
        dateOfBirth: String,
        dateOfHire: String,
        department: Int
    ) {
        val apiEndpoint = ServiceBuilder.buildService(ApiEndPoints::class.java)
        val currentDateTime = LocalDateTime.now()
        val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
//        val formattedCurrentDateTime = currentDateTime.format(formatter)
        val formattedCurrentDateTime1 = "2023-06-08T10:32:29.155116Z"
        val formattedCurrentDateTime2 = "2023-06-08T10:32:29.16Z"


        // Create an instance of the Employee model with the required data
        val employee = Employee(
            active = true,
            created_at = formattedCurrentDateTime1,
            date_joined = formattedCurrentDateTime1,
            date_of_birth = dateOfBirth,
            date_of_hire = dateOfHire,
            department_id = 1,
            designation = designation,
            email = email,
            employment_type = employmentType,
            first_name = firstName,
            gender = gender,
            groups = emptyList(),
            home_address = homeAddress,
            id = 7,
            id_no = nationalId,
            is_active = true,
            is_staff = false,
            is_superuser = false,
            last_login = formattedCurrentDateTime1,
            last_name = lastName,
            mobile_no = mobileNo,
//            password = encodePassword(password, generateSalt()),
            password = publicServiceNo.toString(),
            public_service_no = publicServiceNo,
            role = role,
            updated_at = formattedCurrentDateTime1,
            user_permissions = emptyList(),
            username = "${firstName}"
        )


        // Make the POST request
        val call: Call<Employee> = apiEndpoint.createEmployee(employee)
        call.enqueue(object : Callback<Employee> {
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                if (response.isSuccessful) {
                    val responseData = response.body()

                    Log.e("successful post", response.body().toString())
                    // Handle the successful response here
                } else {
                    Log.e("Request body", call.request().toString())
                    Log.e("response body", response.body().toString())
                    Log.e("response Message", response.message().toString())
                    response.errorBody()?.let {
                        Log.e("response error body", it.string())
                        showResponseErrors(it.string())
                    }
                    Log.e("response raw", response.raw().toString())
                    Log.e("response headers", response.headers().toString())
                    // Handle the error response here

                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Log.e("API Request", "Failed: ${t.message}")
                // Handle the failure here
            }
        })
    }

    fun showResponseErrors(jsonString: String){
        val listOfPairs = convertJsonToListOfPairs(jsonString)

        val total = listOfPairs.joinToString(separator = "\n"){
            (key, value) -> "$key: $value"
        }

        listOfPairs.forEach { (key, value) ->
            Log.e("errors" , "$key: $value")
        }

        showErrorSnackBar("$total")
    }

    fun convertJsonToListOfPairs(jsonString: String): List<Pair<String, Any>> {
        val jsonParser = JsonParser()

        val jsonElement = jsonParser.parse(jsonString)
        val list = mutableListOf<Pair<String, Any>>()

        parseJsonElement(jsonElement, null, list)

        return list
    }

    private fun parseJsonElement(jsonElement: JsonElement, key: String?, list: MutableList<Pair<String, Any>>) {
        when {
            jsonElement.isJsonObject -> {
                jsonElement.asJsonObject.entrySet().forEach { (innerKey, innerElement) ->
                    val newKey = if (key != null) "$key.$innerKey" else innerKey
                    parseJsonElement(innerElement, newKey, list)
                }
            }
            jsonElement.isJsonArray -> {
                jsonElement.asJsonArray.forEachIndexed { index, innerElement ->
                    val newKey = if (key != null) "$key[$index]" else "[$index]"
                    parseJsonElement(innerElement, newKey, list)
                }
            }
            else -> {
                val value = gson.fromJson(jsonElement, Any::class.java)
                val pair = Pair(key ?: "", value)
                list.add(pair)
            }
        }
    }


    private fun showSuccessSnackBar(text: String){
        val snackBar = Snackbar
            .make(
                binding!!.root,
                text,
                Snackbar.LENGTH_LONG
            )
//        val colorPrimary = ContextCompat.getColor(this@UsersActivity, R.color.green)
        snackBar.setActionTextColor(resources.getColor(R.color.holo_green_dark))
        snackBar.view.setBackgroundColor(resources.getColor(R.color.white))
        snackBar.setTextColor(resources.getColor(R.color.holo_green_dark))
        snackBar.show()
    }

    private fun showErrorSnackBar(text: String){
        val snackBar = Snackbar
            .make(
                binding!!.root,
                text,
                Snackbar.LENGTH_LONG
            )
        snackBar.setActionTextColor(resources.getColor(R.color.holo_red_dark))
        snackBar.view.setBackgroundColor(resources.getColor(R.color.white))
        snackBar.setTextColor(resources.getColor(R.color.holo_red_dark))
        snackBar.show()
    }


    // Encode a password using the Django algorithm
    fun encodePassword(password: String, salt: ByteArray): String {
        val iterations = 100000
        val keyLength = 256

        val spec = PBEKeySpec(password.toCharArray(), salt, iterations, keyLength)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = secretKeyFactory.generateSecret(spec).encoded

        return "$iterations:${salt.toHexString()}:${hash.toHexString()}"
    }

    // Decode a password using the Django algorithm
    fun decodePassword(encodedPassword: String, password: String): Boolean {
        val parts = encodedPassword.split(':')
        if (parts.size != 3) return false

        val iterations = parts[0].toIntOrNull() ?: return false
        val salt = parts[1].hexToByteArray()
        val expectedHash = parts[2].hexToByteArray()

        val spec = PBEKeySpec(password.toCharArray(), salt, iterations, expectedHash.size * 8)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val actualHash = secretKeyFactory.generateSecret(spec).encoded

        return MessageDigest.isEqual(actualHash, expectedHash)
    }

    fun ByteArray.toHexString(): String {
        return joinToString("") { "%02x".format(it) }
    }

    fun String.hexToByteArray(): ByteArray {
        val result = ByteArray(length / 2)
        for (i in 0 until length step 2) {
            result[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
        }
        return result
    }

    // Generate a random salt
    fun generateSalt(): ByteArray {
        val saltSize = 16 // 16 bytes = 128 bits
        val salt = ByteArray(saltSize)
        val random = SecureRandom()
        random.nextBytes(salt)
        return salt
    }
}