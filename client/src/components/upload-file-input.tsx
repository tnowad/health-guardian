import { useUploadFileMutation } from "@/lib/apis/upload-file.api";
import { Input } from "./ui/input";

type UploadFileInputProps = {
  onChange: ({ id, url }: { id: string; url: string }) => void;
};

function UploadFileInput({ onChange }: UploadFileInputProps) {
  const uploadFileMutation = useUploadFileMutation();
  const handleFileChange = async (
    event: React.ChangeEvent<HTMLInputElement>,
  ) => {
    const file = event.target.files?.[0];
    if (!file) {
      return;
    }

    const data = await uploadFileMutation.mutateAsync({ file });
    onChange(data);
  };
  return <Input type="file" onChange={handleFileChange} />;
}

export { UploadFileInput };
