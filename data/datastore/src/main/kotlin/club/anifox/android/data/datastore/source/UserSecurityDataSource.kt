package club.anifox.android.data.datastore.source

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.core.DataStore
import club.anifox.android.data.datastore.BuildConfig
import club.anifox.android.domain.model.user.UserSecurityData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class UserSecurityDataSource @Inject constructor(
    private val dataStore: DataStore<UserSecurityData>,
) {
    private val alias = BuildConfig.userdatastorealias

    val accessToken: Flow<String> = dataStore.data.map { if(it.accessToken.isNotEmpty()) decrypt(it.accessToken) else "" }
    val refreshToken: Flow<String> = dataStore.data.map { if(it.refreshToken.isNotEmpty()) decrypt(it.refreshToken) else "" }

    suspend fun saveAccessToken(accessToken: String) {
        dataStore.updateData { currentData ->
            currentData.copy(accessToken = encrypt(accessToken))
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.updateData { currentData ->
            currentData.copy(refreshToken = encrypt(refreshToken))
        }
    }


    private fun getKeyStore(): KeyStore {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        return keyStore
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = getKeyStore()
        if (!keyStore.containsAlias(alias)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(false)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
        return keyStore.getKey(alias, null) as SecretKey
    }

    private fun encrypt(data: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        val encryptedData = cipher.iv + encrypted
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    private fun decrypt(encryptedData: String): String {
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSize = 16
        val iv = decodedData.slice(0 until ivSize).toByteArray()
        val encrypted = decodedData.slice(ivSize until decodedData.size).toByteArray()
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), IvParameterSpec(iv))
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }
}