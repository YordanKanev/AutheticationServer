package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.Entry;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.change.*;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AdminCreator;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class CreateAdminCommand extends AdminCommand {

    public static final String ADMIN_NOT_CREATED_MESSAGE = "Admin not created.";
    public static final String ADMIN_CREATED_MESSAGE = "Admin created.";

    private AdminCreator adminCreator;
    private AuditLog auditLog;
    public CreateAdminCommand(Request request, AdminCreator adminCreator, AuditLog auditLog) throws ParseException {
        super(request, CommandFactory.ADD_ADMIN_USER);
        if(adminCreator == null || auditLog == null){
            throw new IllegalArgumentException();
        }
        this.adminCreator = adminCreator;
        this.auditLog = auditLog;
    }

    private StartedConfigurationChange logStartAction() throws IOException {
        Change change = ChangeFactory.getInstance(this.adminOperation.getUsername(),true);
        StartedConfigurationChange configurationChange = ConfigurationChangeFactory.startConfigurationChange(change,issuer);
        auditLog.log(configurationChange);
        return configurationChange;
    }
    private void logFinishAction(StartedConfigurationChange startedConfigurationChange,boolean successful) {
        ChangeResult changeResult = ChangeResultFactory.getInstance(successful);
        Entry configurationChange = ConfigurationChangeFactory.finishConfigurationChange(startedConfigurationChange,
                changeResult);
    }
    @Override
    public Response execute() {
        try{
            StartedConfigurationChange startedConfigurationChange = logStartAction();
            User user = adminCreator.createAdmin(this.adminOperation);
            Response response = null;
            if(user == null){
                response = ResponseFactory.error(ADMIN_NOT_CREATED_MESSAGE);
            }
            response = ResponseFactory.success(ADMIN_CREATED_MESSAGE);
            logFinishAction(startedConfigurationChange,response.isSuccessful());
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFactory.error(e.getMessage());
        }
    }
}
