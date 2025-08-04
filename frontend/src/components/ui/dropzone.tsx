import { uploadFiles } from "@/pages/knowledge-base/api/DocumentsApi";
import { Upload, X } from "lucide-react";
import { useState } from "react";
import { Button } from "./button";
import { Dropzone, DropzoneContent, DropzoneEmptyState } from "./shadcn-io/dropzone";
import { Spinner } from "./shadcn-io/spinner";

export interface DropzoneProps {
  isOpen: boolean;
  onClose: () => void;
}

const UploadDropzone: React.FC<DropzoneProps> = ({ onClose, isOpen }) => {
  const [files, setFiles] = useState<File[] | undefined>();
  const [working, setWorking] = useState(false);

  const handleDrop = (files: File[]) => {
    setFiles(files);
  };

  const handleClose = () => {
    setFiles(undefined);
    setWorking(false);
    onClose();
  };

  const handleUpload = async () => {
    setWorking(true);

    if (!files) {
      setWorking(false);
      return;
    }

    await uploadFiles(files);

    setFiles(undefined);
    setWorking(false);
    handleClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 bg-black/100 flex items-center flex-col justify-center">
      <div className="bg-input rounded-xl p-5 flex flex-col items-center gap-3 relative w-[90vw] xl:w-[40vw]">
        <Button
          onClick={handleClose}
          className="absolute top-2 right-2 text-white bg-transparent border border-transparent rounded px-2 py-1 text-sm z-10 hover:bg-transparent hover:cursor-pointer"
        >
          <X className="text-white" />
        </Button>
        <p className="text-3xl">Upload documents</p>
        <div className="relative bg-whiteshadow-2xl max-w-full overflow-hidden opacity-100 flex items-center">
          <Dropzone
            maxFiles={100}
            onDrop={handleDrop}
            onError={console.error}
            src={files}
            accept={{ "application/pdf": [] }}
          >
            <DropzoneEmptyState />
            <DropzoneContent />
          </Dropzone>
        </div>
        <Button disabled={working} onClick={handleUpload}>
          {working ? (
            <Spinner />
          ) : (
            <>
              <Upload />
              Upload
            </>
          )}
        </Button>
      </div>
    </div>
  );
};

export default UploadDropzone;
