import java.util.Scanner;

/**
 * A class that "encrypts" data through a simple transformation.
 * <p>
 * The provided code is incomplete. Modify it so that it works properly and passes the unit tests in
 * <code>CaesarCipherTest.java</code>.
 *
 * @see <a href="https://cs125.cs.illinois.edu/MP/3/">MP3 Documentation</a>
 * @see <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Caesar Cipher Documentation</a>
 */
public class CaesarCipher {

    /** Minimum shift that encrypt and decrypt need to handle. */
    public static final int MIN_SHIFT = -1024;

    /** Maximum shift that encrypt and decrypt need to handle. */
    public static final int MAX_SHIFT = 1024;

    /**
     * Encrypt a single line of text using a rotate-N transformation.
     * <p>
     * The printable range of ASCII characters starts at decimal value 32 (' ') and ends at 126
     * ('~'). You should shift characters within this range by the shift value provided. For
     * example, ' ' (32) shift 1 becomes '!' (33), while '~' (126) shift 1 wraps around and becomes
     * ' ' (32). You may want to explore modular arithmetic to simplify the transformation.
     * <p>
     * Your function should return a new character array, not modify the one that it is passed.
     * <p>
     * Both encrypt and decrypt may receive invalid inputs. If the character array contains invalid
     * characters (outside of the range defined above), or if the shift value is outside the range
     * defined above (e.g., larger than MAX_SHIFT), you should return null.
     * <p>
     * <strong>Your solution must match the expected output exactly, otherwise you will not receive
     * credit.</strong>
     * <p>
     * Complete the Javadoc comment for this function and write it.
     *
     * @see <a href="http://www.asciitable.com/">ASCII Character Table</a>
     */

    /**
     * @param line the char array that needs to be encrypted
     * @param shift the offset in the char table to encrypt values
     * @return result the encrypted string in a char Array
     */
    public static char[] encrypt(final char[] line, final int shift) {
        if (shift < MIN_SHIFT || shift > MAX_SHIFT) {
            return null;
        }
        char[] result = line.clone();
        final int maxCharValue = 126;
        final int minCharValue = 32;
        final int charDifference = maxCharValue - minCharValue + 1;
        int charShifted = 0;
        int shift2 = shift;

        for (int i = 0; i < result.length; i++) {
            charShifted = result[i] + shift2;


            while (charShifted > maxCharValue) {
                charShifted = charShifted - charDifference;
            }
            while (charShifted < minCharValue) {
                charShifted = charShifted + charDifference;
            }

            result[i] = (char) (charShifted);
        }
        return result;
    }

    /*
     * Undoes the transformation performed by encrypt above. Add a Javadoc comment for this function
     * and complete it. It should also return a new character array, not modify the one passed in.
     */

    /**
     * @param line encrypted char array line
     * @param shift the amount to shift the char values by
     * @return decrpyted char array
     */
    public static char[] decrypt(final char[] line, final int shift) {
       return encrypt(line, -shift);
    }

    /**********************************************************************************************
     * You do not need to modify code below this comment.
     **********************************************************************************************/

    /**
     * Solicits a single line of text from the user, encrypts it using a random shift, and then
     * decrypts it.
     * <p>
     * You are free to review this function, but should not modify it. Note that this function is
     * not tested by the test suite, as it is purely to aid your own interactive testing.
     *
     * @param unused unused input arguments
     */
    @SuppressWarnings("resource")
    public static void main(final String[] unused) {

        String linePrompt = String.format("Enter a line of text, or a blank line to exit:");
        String shiftPrompt = String.format("Enter an integer to shift by:");

        /*
         * Two steps here: first get a line, then a shift integer.
         */
        Scanner lineScanner = new Scanner(System.in);
        repeat : while (true) {
            String line = null;
            Integer shift = null;

            System.out.println(linePrompt);
            while (lineScanner.hasNextLine()) {
                line = lineScanner.nextLine();
                if (line.equals("")) {
                    break repeat;
                } else {
                    break;
                }
            }

            System.out.println(shiftPrompt);
            while (lineScanner.hasNextLine()) {
                Scanner intScanner = new Scanner(lineScanner.nextLine());
                if (intScanner.hasNextInt()) {
                    shift = intScanner.nextInt();
                    if (intScanner.hasNext()) {
                        shift = null;
                        System.out.println("Invalid input: please enter only a single integer.");
                    }
                } else {
                    System.out.println("Invalid input: please enter an integer.");
                }
                intScanner.close();
                if (shift != null) {
                    break;
                }
            }

            if (line == null || line.equals("")) {
                throw new RuntimeException("Should have a line at this point");
            }
            if (shift == null) {
                throw new RuntimeException("Should have a shift value at this point");
            }

            char[] originalCharacterArray = line.toCharArray();
            char[] encryptedCharacterArray = encrypt(originalCharacterArray, shift);
            char[] decryptedCharacterArray = decrypt(encryptedCharacterArray, shift);

            System.out.println("Encrypted line with ROT-" + shift + ":");
            System.out.println(String.valueOf(encryptedCharacterArray));
            System.out.println("Original line:");
            System.out.println(String.valueOf(originalCharacterArray));
            System.out.println("Decrypted line:");
            System.out.println(String.valueOf(decryptedCharacterArray));
        }
        lineScanner.close();
    }
}
