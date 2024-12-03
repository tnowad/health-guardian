import { Card, CardContent } from "@/components/ui/card";
import { FileText } from "lucide-react";

export default function EmptyState() {
  return (
    <Card className="mt-8">
      <CardContent className="flex flex-col items-center justify-center p-6">
        <FileText className="h-12 w-12 text-gray-400 mb-4" />
        <h2 className="text-xl font-semibold text-gray-700">
          No consent forms available
        </h2>
        <p className="text-gray-500 mt-2">
          There are currently no consent forms to display.
        </p>
      </CardContent>
    </Card>
  );
}
