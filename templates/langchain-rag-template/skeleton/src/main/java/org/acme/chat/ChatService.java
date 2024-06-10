package ${{values.java_package_name}}.chat;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface ChatService {

    @SystemMessage("<<SYS>>You are a chat bot answering to customer requests.<</SYS>>")
    String chat(@UserMessage String message);

}