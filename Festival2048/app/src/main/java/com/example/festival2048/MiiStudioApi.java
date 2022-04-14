package com.example.festival2048;

import android.util.Log;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

public class MiiStudioApi {
    static final String TESTCODE =
            "080240030803032c040e0308050409020e040a070001000804000a01001e4004000214031304080f06030b05030a";

    private String miiCode;
    private String miiUrl;

    public MiiStudioApi(String miiCode) {
        this.miiCode = miiCode;
    }

    public MiiStudioApi() {
        this.miiCode = TESTCODE;
    }

    /**
     * extracts bytes from a Switch-format Mii in MiiStudioMiiLoader text format
     * https://github.com/HEYimHeroic/MiiStudioMiiLoader
     * @param code MSML code
     * @return ArrayList containing Switch-format Mii bytes
     */
    public ArrayList<Integer> codeToBytes(String code)
    {
        ArrayList<Integer> bytes = new ArrayList<>();
        if (code.length() != TESTCODE.length())
            throw new NumberFormatException();

        bytes.add(0);
        for (int i = 0; i < code.length(); i += 2) {
            bytes.add(Integer.parseInt(code.substring(i, i+2), 16));
        }

        return bytes;
    }

    /**
     * converts Switch-format Mii bytes to a MiiStudio render URL
     * @param bytes bytes of a Switch-format Mii
     * @return URL to the Mii render
     */
    public String bytesToUrl(ArrayList<Integer> bytes)
    {
        String miiData = "";
        int eo, i = 0;
        int n = 256;

        // https://github.com/HEYimHeroic/mii2studio/blob/74014fe199a358e809b2af2022a6eafaa7ec9931/mii2studio.py#L308
        for (Integer v: bytes) {
            eo = (7 + (v ^ n)) % 256;
            n = eo;
            String b = String.format("%1$02X", eo).toLowerCase(Locale.ROOT);
            miiData += b;
            Log.println(Log.DEBUG, "mii data decode:", "byte " + i + ": " + b);
            i++;
        }

        String encodedData = new String(miiData.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_8);

        Log.println(Log.DEBUG, "encoded mii data:", encodedData);
        return "https://studio.mii.nintendo.com/miis/image.png?data=" + encodedData +
                "&type=face&expression=normal&width=128&bgColor=FFFFFF00&clothesColor=default&cameraXRotate=0&cameraYRotate=0&cameraZRotate=0&characterXRotate=0&characterYRotate=0&characterZRotate=0&lightDirectionMode=none&instanceCount=1&instanceRotationMode=model";
    }

    /**
     * returns the URL to the Mii Studio render,
     * or generates one from the MSML code if it doesn't exist yet
     * @return URL to the Mii Studio render
     */
    public String getMiiUrl() {
        if (miiUrl == null)
        {
            try {
                miiUrl = bytesToUrl(codeToBytes(miiCode));
            }
            catch (NumberFormatException nfe)
            {
                throw nfe;
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        return miiUrl;
    }

    public String getMiiCode() {
        return miiCode;
    }

    public void setMiiCode(String miiCode) {
        this.miiCode = miiCode;
        this.miiUrl = null;
    }
}
