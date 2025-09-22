package leegroup.module.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import leegroup.module.data.encrypt.EncryptionUtils
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@Ignore("can't run robolectric")
class EncryptionUtilsInstrumentedTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        EncryptionUtils.init(context)
    }

    @Test
    fun encryptDecrypt_roundTrip_works() {
        val original = "Hello, Tink!".toByteArray()

        val encrypted = EncryptionUtils.encrypt(original)
        assertNotEquals("Data should be encrypted", original, encrypted)

        val decrypted = EncryptionUtils.decrypt(encrypted)
        assertArrayEquals("Decrypted data should match original", original, decrypted)
    }

    @Test(expected = Exception::class)
    fun decrypt_invalidData_fails() {
        val invalidData = "invalid".toByteArray()
        EncryptionUtils.decrypt(invalidData) // should throw GeneralSecurityException
    }
}