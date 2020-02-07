package bg.sofia.uni.fmi.mjt.authentication.server.commands;

import bg.sofia.uni.fmi.mjt.authentication.server.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.Entry;
import bg.sofia.uni.fmi.mjt.authentication.server.audit.change.*;
import bg.sofia.uni.fmi.mjt.authentication.server.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.server.model.web.response.ResponseFactory;
import bg.sofia.uni.fmi.mjt.authentication.server.interfaces.AdminRemover;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class RemoveAdminCommand extends AdminCommand {

    public static final String ADMIN_REMOVED_MESSAGE = "Admin removed.";
    public static final String ADMIN_NOT_REMOVED_MESSAGE = "Admin not removed";

    private AdminRemover adminRemover;
    private AuditLog auditLog;

    public RemoveAdminCommand(Request request, AdminRemover adminRemover, AuditLog auditLog) throws ParseException {
        super(request, CommandFactory.REMOVED_ADMIN_USER);
        if(adminRemover == null || auditLog == null){
            throw new IllegalArgumentException();
        }
        this.adminRemover = adminRemover;
        this.auditLog = auditLog;
    }
    private StartedConfigurationChange logStartAction() throws IOException {
        Change change = ChangeFactory.getInstance(this.adminOperation.getUsername(),true);
        StartedConfigurationChange configurationChange = ConfigurationChangeFactory.startConfigurationChange(change,issuer);
        auditLog.log(configurationChange);
        return configurationChange;
    }
    private void logFinishAction(StartedConfigurationChange startedConfigurationChange,boolean successful) throws IOException {
        ChangeResult changeResult = ChangeResultFactory.getInstance(successful);
        FinishedConfigurationChange configurationChange = ConfigurationChangeFactory.finishConfigurationChange(startedConfigurationChange,
                changeResult);
        auditLog.log(configurationChange);
    }

    @Override
    public Response execute() {
        try{
            StartedConfigurationChange startedConfigurationChange = logStartAction();
            User user = adminRemover.removeAdmin(this.adminOperation);
            Response response = null;
            if(user == null){
                response = ResponseFactory.error(ADMIN_NOT_REMOVED_MESSAGE);
            }else{
                response = ResponseFactory.success(ADMIN_REMOVED_MESSAGE);
            }
            logFinishAction(startedConfigurationChange,response.isSuccessful());
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFactory.error(e.getMessage());
        }
    }
}
