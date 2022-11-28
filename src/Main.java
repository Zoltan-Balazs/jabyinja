import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HexFormat;

class InvalidClassFileException extends RuntimeException { }

// https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
class ClassFile {
 	private static final byte[] MAGIC_NUMBER = HexFormat.of().parseHex("CAFEBABE");
    
    private static boolean VALID_CLASS_FILE = false;
	
	public ClassFile(String fileName) {
		readClassFile(fileName);
	}
	
	public void readClassFile(String fileName) {
        try (InputStream classFile = new FileInputStream(fileName)) {
            if (!Arrays.equals(classFile.readNBytes(4), MAGIC_NUMBER)) {
				throw new InvalidClassFileException();
			}
            VALID_CLASS_FILE = true;
        } catch (FileNotFoundException e) {
			System.err.println("The file " + fileName + " cannot be found!");
		} catch (InvalidClassFileException e) {
			System.err.println("The file '" + fileName + "' is not a valid Java Class file!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class Main {
    private static ClassFile CLASS_FILE;
    
    public static void main(String[] args) {
		if (args.length == 0) {
			CLASS_FILE = new ClassFile("Main.class");
		} else {
			CLASS_FILE = new ClassFile(args[0]);
		}
	}
}
