import { X } from "lucide-react";
import React from "react";
import { Button } from "./button";

export type PDFModalProps = {
  isOpen: boolean;
  onClose: () => void;
  pdfUrl: string;
};

const PDFModal: React.FC<PDFModalProps> = ({ isOpen, onClose, pdfUrl }) => {
  if (!isOpen) return null;

  return (
    <div
      className="fixed inset-0 z-50 bg-black/80  flex items-center justify-center"
      onPointerUp={onClose}
    >
      <div className="relative w-[90vw] h-[90vh] bg-white shadow-2xl rounded-xl overflow-hidden opacity-100">
        <Button
          onClick={onClose}
          className="fixed top-2 right-2 text-white bg-transparent border border-transparent rounded px-2 py-1 text-sm z-10 hover:bg-transparent hover:cursor-pointer"
        >
          <X className="text-white" />
        </Button>

        <iframe src={pdfUrl} title="PDF Viewer" className="w-full h-full" />
      </div>
    </div>
  );
};

export default PDFModal;
