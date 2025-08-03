import { Button } from "@/components/ui/button";
import { DialogHeader } from "@/components/ui/dialog";
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
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
  DialogTrigger,
} from "@radix-ui/react-dialog";
import { Search, Trash } from "lucide-react";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { deleteDocument, getDocument, getDocuments } from "./api/DocumentsApi";
import { removeDocument, setDocuments, type Document } from "./state/documentsSlice";

export default function KnowledgeBase() {
  const [showModal, setShowModal] = useState(false);
  const [pdfUrl, setPdfUrl] = useState("");

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

  useEffect(() => {
    fetchDocuments();
  }, []);

  return (
    <>
      <PDFModal isOpen={showModal} onClose={handlePdfClose} pdfUrl={pdfUrl} />
      <div className="w-[90%] xl:w-[60%] mt-22">
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
