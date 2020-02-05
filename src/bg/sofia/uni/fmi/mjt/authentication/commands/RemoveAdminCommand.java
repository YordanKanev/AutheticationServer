package bg.sofia.uni.fmi.mjt.authentication.commands;

import bg.sofia.uni.fmi.mjt.authentication.audit.AuditLog;
import bg.sofia.uni.fmi.mjt.authentication.audit.Entry;
import bg.sofia.uni.fmi.mjt.authentication.audit.Issuer;
import bg.sofia.uni.fmi.mjt.authentication.audit.change.*;
import bg.sofia.uni.fmi.mjt.authentication.model.user.User;
import bg.sofia.uni.fmi.mjt.authentication.model.web.request.Request;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.Response;
import bg.sofia.uni.fmi.mjt.authentication.model.web.response.ResponseFactory;
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
        Change change = ChangeFactory.getInstance(this.adminOperation.getUsername(),false);
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
            User user = adminRemover.removeAdmin(this.adminOperation);
            Response response = null;
            if(user == null){
                response = ResponseFactory.error(ADMIN_NOT_REMOVED_MESSAGE);
            }
            response = ResponseFactory.success(ADMIN_REMOVED_MESSAGE);
            logFinishAction(startedConfigurationChange,response.isSuccessful());
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFactory.error(e.getMessage());
        }
    }
}
