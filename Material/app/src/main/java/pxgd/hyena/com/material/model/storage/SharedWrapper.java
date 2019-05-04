package pxgd.hyena.com.material.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import pxgd.hyena.com.material.model.util.EntityUtils;
import pxgd.hyena.com.material.util.Crypto;
import pxgd.hyena.com.material.util.Digest;
import pxgd.hyena.com.material.util.StandardCharsets;

public final class SharedWrapper {

    private static final String TAG = "SharedWrapper";

    private volatile static SecretKey secretSingleton = null;
    private volatile static IvParameterSpec ivSingleton = null;

    public static SharedWrapper with(@NonNull Context context, @NonNull String name) {
        if (secretSingleton == null || ivSingleton == null) {
            synchronized (SharedWrapper.class) {
                if (secretSingleton == null) {
                    secretSingleton = Crypto.AES.generateSecret(Digest.SHA256.getRaw(DeviceInfo.getDeviceToken(context)));
                }
                if (ivSingleton == null) {
                    ivSingleton = Crypto.AES.generateIv(Digest.MD5.getRaw(DeviceInfo.getDeviceToken(context)));
                }
            }
        }
        return new SharedWrapper(context, name, secretSingleton, ivSingleton);
    }

    private final SharedPreferences sp;
    private final SecretKey secret;
    private final IvParameterSpec iv;

    private SharedWrapper(@NonNull Context context, @NonNull String name, @NonNull SecretKey secret, @NonNull IvParameterSpec iv) {
        sp = context.getSharedPreferences(Digest.MD5.getHex(name), Context.MODE_PRIVATE);
        this.secret = secret;
        this.iv = iv;
    }

    private String get(@NonNull String key, @Nullable String defValue) {
        String target = sp.getString(Digest.MD5.getHex(key), null);
        if (target == null) {
            return defValue;
        } else {
            try {
                return new String(Crypto.AES.decrypt(secret, iv, Base64.decode(target, Base64.DEFAULT)), StandardCharsets.UTF_8);
            } catch (Crypto.CryptoException e) {
                Log.e(TAG, "Decrypt error at key :" + key, e);
                return defValue;
            }
        }
    }

    private void set(@NonNull String key, @Nullable String value) {
        String target;
        if (value == null) {
            target = null;
        } else {
            try {
                target = Base64.encodeToString(Crypto.AES.encrypt(secret, iv, value.getBytes(StandardCharsets.UTF_8)), Base64.DEFAULT);
            } catch (Crypto.CryptoException e) {
                Log.e(TAG, "Encrypt error at key :" + key, e);
                target = null;
            }
        }
        sp.edit().putString(Digest.MD5.getHex(key), target).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }

    public String getString(@NonNull String key, @Nullable String defValue) {
        return get(key, defValue);
    }

    public void setString(@NonNull String key, @Nullable String value) {
        set(key, value);
    }

    public boolean getBoolean(@NonNull String key, boolean defValue) {
        String value = get(key, null);
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        } else {
            if (value != null) {
                Log.e(TAG, "Parse boolean error -> " + key + " : " + value);
            }
            return defValue;
        }
    }

    public void setBoolean(@NonNull String key, boolean value) {
        set(key, Boolean.toString(value));
    }

    public float getFloat(@NonNull String key, float defValue) {
        String value = get(key, null);
        if (value == null) {
            return defValue;
        } else {
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Parse float error -> " + key + " : " + value);
                return defValue;
            }
        }
    }

    public void setFloat(@NonNull String key, float value) {
        set(key, Float.toString(value));
    }

    public int getInt(@NonNull String key, int defValue) {
        String value = get(key, null);
        if (value == null) {
            return defValue;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Parse int error -> " + key + " : " + value);
                return defValue;
            }
        }
    }

    public void setInt(@NonNull String key, int value) {
        set(key, Integer.toString(value));
    }

    public long getLong(@NonNull String key, long defValue) {
        String value = get(key, null);
        if (value == null) {
            return defValue;
        } else {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Parse long error -> " + key + " : " + value);
                return defValue;
            }
        }
    }

    public void setLong(@NonNull String key, long value) {
        set(key, Long.toString(value));
    }

    public <T> T getObject(@NonNull String key, @NonNull Type typeOfT) {
        String value = get(key, null);
        if (value == null) {
            return null;
        } else {
            try {
                return EntityUtils.gson.fromJson(value, typeOfT);
            } catch (JsonParseException e) {
                Log.e(TAG, "Parse object error -> " + key + " : " + value);
                return null;
            }
        }
    }

    public void setObject(@NonNull String key, @Nullable Object value) {
        set(key, value == null ? null : EntityUtils.gson.toJson(value));
    }

}
