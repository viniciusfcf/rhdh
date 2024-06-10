package ${{values.java_package_name}};


import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

import java.time.LocalDateTime;
import java.util.Random;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;


@Path("/")
public class DocumentResource {

    @Inject
    EmbeddingModel embeddingModel;

    /**
     * The embedding store (the database).
     * The bean is provided by the quarkus-langchain4j-pgvector extension.
     */
    @Inject
    EmbeddingStore<TextSegment> store;

    @GET
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public String save(@QueryParam(value = "text") String text) throws InterruptedException {
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .documentSplitter(recursive(500, 0))
                .build();
        Metadata metadata = new Metadata();
        metadata.put("user", "Vinicius");
        metadata.put("request id", new Random().nextInt());
        metadata.put("request date", LocalDateTime.now().toString());
        Document document = Document.document(text, metadata);
        ingestor.ingest(document);
        Thread.sleep(1000); // to be sure that embeddings were persisted
        return "ok";
    }


}
