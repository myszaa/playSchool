package security;

import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import be.objectify.deadbolt.java.models.Subject;
import model.User;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

public class MyDeadboltHandler extends AbstractDeadboltHandler {

    public MyDeadboltHandler(ExecutionContextProvider ecProvider) {
        super(ecProvider);
    }

    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(Http.Context context) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(Http.Context context) {
        String login = Http.Context.current().session().get("userId");
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(User.findByUsername(login)), (Executor) executionContextProvider.get());
    }

    @Override
    public CompletionStage<Result> onAuthFailure(Http.Context context, Optional<String> content) {
        return CompletableFuture.completedFuture(redirect("/authFailure"));
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.Context context) {
        return null;
    }
}
