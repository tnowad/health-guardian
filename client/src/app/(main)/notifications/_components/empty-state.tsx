import { Card, CardContent } from "@/components/ui/card";
import { Bell } from "lucide-react";

export default function EmptyState() {
  return (
    <Card className="mt-8">
      <CardContent className="flex flex-col items-center justify-center p-6">
        <Bell className="h-12 w-12 text-gray-400 mb-4" />
        <h2 className="text-xl font-semibold text-gray-700">
          No notifications
        </h2>
        <p className="text-gray-500 mt-2">You're all caught up!</p>
      </CardContent>
    </Card>
  );
}
