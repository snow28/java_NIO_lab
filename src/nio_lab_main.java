
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.util.Map;


public class nio_lab_main {

    
    public static void main(String[] args) throws IOException {
        
        //create directory
        Path path = Paths.get("./newDir");
        Path newFile = Paths.get("./newDir/newFile.txt");
        Path destinationPath = Paths.get("./copiedFile.txt");
         
        try{
            Path newDir = Files.createDirectory(path);
        }catch(FileAlreadyExistsException e){
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        //Create file in thisdirectory
        if (Files.notExists(newFile)) {
            try { Files.createFile(newFile); }
            catch (Exception e ) { e.printStackTrace(); }
        }
        
        //Copy this file to another file
       try {
        Files.copy(newFile, destinationPath);
       } catch(FileAlreadyExistsException e) {
        
       } catch (IOException e) {
        
        e.printStackTrace();
       }
       
       
       //Show information about attributes of file
       FileStore fs = Files.getFileStore(destinationPath);
       // Check if POSIX file attribute is supported by the file store
       boolean supported = fs
            .supportsFileAttributeView(PosixFileAttributeView.class);
        if (supported) {
          System.out.println("POSIX file attribute view  is supported.");
        } else {
          System.out.println("POSIX file attribute view  is not  supported.");
        }
        
        // Prepare the attribute list
        String attribList = "basic:size,lastModifiedTime";

        // Read the attributes
        Map<String, Object> attribs = Files.readAttributes(destinationPath, attribList);

        System.out.format("Size:%s, Last   Modified   Time:%s %n",
            attribs.get("size"), attribs.get("lastModifiedTime"));
        
        
        //create new directory
        Path pathDir2 = Paths.get("./newDir2");
        try{
            Path newDir2 = Files.createDirectory(pathDir2);
        }catch(FileAlreadyExistsException e){
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        //move file to another file
        
        Path movefrom = FileSystems.getDefault().getPath("./copiedFile.txt");
        Path target = FileSystems.getDefault().getPath("./newDir2/movedFile.txt");
        //Path target_dir = FileSystems.getDefault().getPath("C:/tutorial/photos");

        //method 1
        try {
            Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        //delete file and directory
        try {
            Files.delete(newFile);
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }

        
        
        
    }
    
}
