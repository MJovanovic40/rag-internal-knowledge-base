import { Button } from "@/components/ui/button";
import UploadDropzone from "@/components/ui/dropzone";
import PDFModal from "@/components/ui/pdf-modal";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useAppSelector } from "@/hooks";
import { Plus, Search, Trash, Upload } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { deleteDocument, getDocument, getDocuments } from "./api/DocumentsApi";
import { removeDocument, setDocuments, type Document } from "./state/documentsSlice";

export default function KnowledgeBase() {
  const [showModal, setShowModal] = useState(false);
  const [pdfUrl, setPdfUrl] = useState("");

  const [showDropzone, setShowDropzone] = useState<boolean>(false);

  const documents = useAppSelector((state) => state.documents.documents);
  const dispatch = useDispatch();

  const fetchDocuments = async () => {
    const docs = await getDocuments();
    dispatch(setDocuments(docs));
  };

  const handleDelete = async (document: Document) => {
    await deleteDocument(document.id);
    dispatch(removeDocument(document));
  };

  const handleView = async (document: Document) => {
    const resp = await getDocument(document.id);
    setPdfUrl(resp.url);
    setShowModal(true);
  };

  const handlePdfClose = () => {
    setShowModal(false);
  };

  const handleOpenDropzone = () => {
    setShowDropzone(true);
  };

  const handleCloseDropzone = () => {
    setShowDropzone(false);
  };

  useEffect(() => {
    fetchDocuments();
  }, []);

  return (
    <>
      <PDFModal isOpen={showModal} onClose={handlePdfClose} pdfUrl={pdfUrl} />
      <UploadDropzone isOpen={showDropzone} onClose={handleCloseDropzone} />
      <div className="w-[90%] xl:w-[60%] mt-22">
        Number of documents: {documents.length}
        <Button className="float-end" onClick={handleOpenDropzone}>
          <Upload />
          Upload
        </Button>
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>File Name</TableHead>
              <TableHead>Uploaded At</TableHead>
              <TableHead>Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {documents &&
              documents.map((doc) => {
                return (
                  <TableRow key={doc.id}>
                    <TableCell>{doc.name}</TableCell>
                    <TableCell>{doc.createdAt}</TableCell>
                    <TableCell>
                      <div className="flex">
                        <Button
                          className="bg-transparent hover:cursor-pointer hover:bg-transparent"
                          onClick={() => handleView(doc)}
                        >
                          <Search className="text-white" />
                        </Button>
                        <Button
                          className="bg-transparent hover:cursor-pointer hover:bg-transparent"
                          onClick={() => handleDelete(doc)}
                        >
                          <Trash className="text-white" />
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </div>
    </>
  );
}
