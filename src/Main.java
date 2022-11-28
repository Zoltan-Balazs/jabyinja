import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;

class InvalidClassFileException extends RuntimeException { }

class ClassFile_Helper {
	public static byte readByte(InputStream file) throws IOException {
		byte[] tmp = file.readNBytes(1);
		return tmp[0];
	}

	public static short readShort(InputStream file) throws IOException {
		ByteBuffer wrapper;
		byte[] tmp = file.readNBytes(2);
		wrapper = ByteBuffer.wrap(tmp);
		return wrapper.getShort();
	}

	public static int readInt(InputStream file) throws IOException {
		ByteBuffer wrapper;
		byte[] tmp = file.readNBytes(4);
		wrapper = ByteBuffer.wrap(tmp);
		return wrapper.getInt();
	}
}

// https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
class ClassFile {
 	private static final byte[] MAGIC_NUMBER = HexFormat.of().parseHex("CAFEBABE");
    
    private static boolean VALID_CLASS_FILE = false;
	private static short MINOR_VERSION;
	private static short MAJOR_VERSION;
	private static short CONSTANT_POOL_COUNT;
	private static List<CP_Info> CONSTANT_POOL;
	private static short ACCESS_FLAGS;
	private static short THIS_CLASS;
	private static short SUPER_CLASS;
	private static short INTERFACES_COUNT;
	private static List<Interface> INTERFACES;
	
	public ClassFile(String fileName) {
		readClassFile(fileName);
	}
	
	public void readClassFile(String fileName) {
        try (InputStream classFile = new FileInputStream(fileName)) {
            if (!Arrays.equals(classFile.readNBytes(4), MAGIC_NUMBER)) {
				throw new InvalidClassFileException();
			}
            VALID_CLASS_FILE = true;

			MINOR_VERSION = ClassFile_Helper.readShort(classFile);
			MAJOR_VERSION = ClassFile_Helper.readShort(classFile);
			CONSTANT_POOL_COUNT = ClassFile_Helper.readShort(classFile);
			CONSTANT_POOL = Constant_Pool_Helper.readConstantPool(classFile, CONSTANT_POOL_COUNT);
			ACCESS_FLAGS = ClassFile_Helper.readShort(classFile);
			THIS_CLASS = ClassFile_Helper.readShort(classFile);
			SUPER_CLASS = ClassFile_Helper.readShort(classFile);
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
