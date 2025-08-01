package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtils {

    public String getObjectName(String fileName, String id) {
        fileName = fileName.replace(" ", "_");
        return fileName + "-" + id;
    }
}
