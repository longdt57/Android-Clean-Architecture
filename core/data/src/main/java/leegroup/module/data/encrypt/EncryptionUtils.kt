package tori.module.datasource.encrypt

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager

object EncryptionUtils {
    private lateinit var aead: Aead

    fun init(context: Context) {
        if (this::aead.isInitialized) return

        AeadConfig.register()
        val keysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(context, "master_keyset", "master_key_preference")
            .withKeyTemplate(com.google.crypto.tink.aead.AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri("android-keystore://master_key")
            .build()
            .keysetHandle

        aead = keysetHandle.getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }

    fun encrypt(data: ByteArray): ByteArray = aead.encrypt(data, null)
    fun decrypt(data: ByteArray): ByteArray = aead.decrypt(data, null)
}