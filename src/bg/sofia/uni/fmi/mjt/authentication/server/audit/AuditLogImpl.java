package bg.sofia.uni.fmi.mjt.authentication.server.audit;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AuditLogImpl implements AuditLog{

    public static final String PATH_NULL_EXCEPTION_MESSAGE = "AuditDirectory is null";
    public static final String INVALID_PATH_EXCEPTION_MESSAGE = "Invalid path - should be a directory";
    public static final String ENTRY_NULL_EXCEPTION_MESSAGE = "Entry is null.";

    private static final String FILE_NAME = "log_";
    private static final Gson GSON = new Gson();


    private Path auditDirectory;
    
    public AuditLogImpl(AuditConfiguration auditConfiguration){
        if(auditConfiguration == null){
            throw new IllegalArgumentException(PATH_NULL_EXCEPTION_MESSAGE);
        }
        Path auditDirectory = auditConfiguration.getAuditDirectoryPath();
        if(!Files.isDirectory(auditDirectory)){
            throw new IllegalArgumentException(INVALID_PATH_EXCEPTION_MESSAGE);
        }
        this.auditDirectory = auditDirectory;
    }

    @Override
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
