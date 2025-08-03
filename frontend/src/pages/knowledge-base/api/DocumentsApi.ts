import { axiosClient } from "@/http/AxiosProvider";
import type { Document } from "../state/documentsSlice";

export interface GetDocumentResponse {
  url: string;
}

export const getDocuments = async (): Promise<Document[]> => {
  return (await axiosClient.get("/api/v1/knowledge-base")).data;
};

export const getDocument = async (id: string): Promise<GetDocumentResponse> => {
  return (await axiosClient.get(`/api/v1/knowledge-base/${id}`)).data;
};

export const deleteDocument = async (id: string) => {
  await axiosClient.delete(`/api/v1/knowledge-base/${id}`);
};

export const uploadFiles = async (files: string | Blob) => {
  const formData = new FormData();
  formData.append("files", files);

  await axiosClient.post("/api/v1/knowledge-base", {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
