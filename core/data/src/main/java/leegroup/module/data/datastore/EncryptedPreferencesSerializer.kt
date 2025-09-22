package leegroup.module.data.datastore

import androidx.datastore.core.Serializer
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.datastore.preferences.core.emptyPreferences
import leegroup.module.data.encrypt.EncryptionUtils
import okio.Buffer
import java.io.InputStream
import java.io.OutputStream

internal object EncryptedPreferencesSerializer : Serializer<Preferences> {
    override val defaultValue: Preferences = emptyPreferences()

    override suspend fun readFrom(input: InputStream): Preferences {
        val encrypted = input.readBytes()
        if (encrypted.isEmpty()) return defaultValue

        // Decrypt first
        val decryptedBytes = EncryptionUtils.decrypt(encrypted)

        // Wrap bytes in a BufferedSource for PreferencesSerializer
        val buffer = Buffer().write(decryptedBytes)
        return PreferencesSerializer.readFrom(buffer)
    }

    override suspend fun writeTo(t: Preferences, output: OutputStream) {
        val buffer = Buffer()
        PreferencesSerializer.writeTo(t, buffer)
        val bytes = buffer.readByteArray()

        // Encrypt before writing to disk
        val encrypted = EncryptionUtils.encrypt(bytes)
        output.write(encrypted)
    }
}
