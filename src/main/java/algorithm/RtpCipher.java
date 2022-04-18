package algorithm;

public class RtpCipher {
    public static String encryptString(String input, int key) {
        char [][] encryptionTable = getEncryptionTable(input, key);

        StringBuilder encryptedString = new StringBuilder();

        if (encryptionTable.length > 0) {
            for (int index1 = 0; index1 < encryptionTable[0].length; ++index1) {
                for (char[] chars : encryptionTable) {
                    encryptedString.append(chars[index1]);
                }
            }
        }

        return encryptedString.toString();
    }

    private static char [][] getEncryptionTable(String input, int key) {
        char [][] encryptionTable = new char[(int) Math.ceil((double)input.length() / key)][key];

        int strIndex = 0;

        for (int index1 = 0; index1 < encryptionTable.length; ++index1) {
            for (int index2 = 0; index2 < encryptionTable[index1].length; ++index2) {
                if (strIndex < input.length()) {
                    encryptionTable[index1][index2] = input.charAt(strIndex);
                }
                else {
                    encryptionTable[index1][index2] = '_';
                }

                ++strIndex;
            }
        }

        return encryptionTable;
    }

    public static String decryptString(String input, int key) {
        char [][] decryptionTable = getDecryptionTable(input, key);

        StringBuilder decryptedString = new StringBuilder();

        for (char[] chars : decryptionTable) {
            for (char curChar : chars) {
                decryptedString.append(curChar);
            }
        }

        return decryptedString.toString();
    }

    private static char [][] getDecryptionTable(String input, int key) {
        char [][] decryptionTable = new char[(int) Math.ceil((double)input.length() / key)][key];

        int strIndex = 0;

        if (decryptionTable.length > 0) {
            for (int index1 = 0; index1 < decryptionTable[0].length; ++index1) {
                for (int index2 = 0; index2 < decryptionTable.length; ++index2) {
                    if (strIndex < input.length()) {
                        decryptionTable[index2][index1] = input.charAt(strIndex);
                    }
                    else {
                        decryptionTable[index2][index1] = '_';
                    }

                    ++strIndex;
                }
            }
        }

        return decryptionTable;
    }
}
