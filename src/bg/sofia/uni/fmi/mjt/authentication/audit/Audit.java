package bg.sofia.uni.fmi.mjt.authentication.audit;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Audit {

    public static final String PATH_NULL_EXCEPTION_MESSAGE = "AuditDirectory is null";
    public static final String INVALID_PATH_EXCEPTION_MESSAGE = "Invalid path - should be a directory";
    public static final String ENTRY_NULL_EXCEPTION_MESSAGE = "Entry is null.";

    private static final String FILE_NAME = "log_";
    private static final Gson GSON = new Gson();

    private Path auditDirectory;
    
    public Audit(Path auditDirectory){
        if(auditDirectory == null){
            throw new IllegalArgumentException(PATH_NULL_EXCEPTION_MESSAGE);
        }
        if(!Files.isDirectory(auditDirectory)){
            throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
        }
        this.auditDirectory = auditDirectory;
    }


    public void log(Entry entry) throws IOException {
        if(entry == null){
            throw new IllegalArgumentException(ENTRY_NULL_EXCEPTION_MESSAGE);
        }
        String json = GSON.toJson(entry);
        String fileName = formatFileName(entry);
        Path filePath = Path.of(auditDirectory.toString(),fileName);
        Files.write(filePath,json.getBytes());
    }

    private static String formatFileName(Entry entry){
        return FILE_NAME + entry.getTimestamp().toString() + ".log";
    }
}
