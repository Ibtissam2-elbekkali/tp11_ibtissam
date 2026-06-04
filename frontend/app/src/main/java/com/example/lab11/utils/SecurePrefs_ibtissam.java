package com.example.lab11.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public final class SecurePrefs_ibtissam {
    private static final String PREFS_NAME_ibtissam = "secure_prefs_ibtissam";
    private static final String KEY_TOKEN_ibtissam = "api_token_ibtissam";

    private SecurePrefs_ibtissam() {}

    private static SharedPreferences getPrefs_ibtissam(Context context_ibtissam) throws Exception {
        MasterKey masterKey_ibtissam = new MasterKey.Builder(context_ibtissam)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        return EncryptedSharedPreferences.create(
                context_ibtissam,
                PREFS_NAME_ibtissam,
                masterKey_ibtissam,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public static void saveToken_ibtissam(Context context_ibtissam, String token_ibtissam) throws Exception {
        getPrefs_ibtissam(context_ibtissam).edit().putString(KEY_TOKEN_ibtissam, token_ibtissam).apply();
    }

    public static String loadToken_ibtissam(Context context_ibtissam) throws Exception {
        return getPrefs_ibtissam(context_ibtissam).getString(KEY_TOKEN_ibtissam, "");
    }
}
