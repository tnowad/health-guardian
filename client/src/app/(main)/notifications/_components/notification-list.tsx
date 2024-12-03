import { Card, CardContent } from "@/components/ui/card";
import { Bell, CheckCircle } from "lucide-react";
import { Button } from "@/components/ui/button";

interface Notification {
  id: string;
  title: string;
  timestamp: string;
  read: boolean;
}

interface NotificationListProps {
  notifications: Notification[];
  toggleRead: (id: string) => void;
}

export default function NotificationList({
  notifications,
  toggleRead,
}: NotificationListProps) {
  return (
    <div className="space-y-4">
      {notifications.map((notification) => (
        <Card
          key={notification.id}
          className={notification.read ? "bg-gray-100" : "bg-white"}
        >
          <CardContent className="flex items-center justify-between p-4">
            <div className="flex items-center space-x-4">
              <div
                className={`p-2 rounded-full ${notification.read ? "bg-gray-300" : "bg-blue-500"}`}
              >
                <Bell
                  className={`h-5 w-5 ${notification.read ? "text-gray-600" : "text-white"}`}
                />
              </div>
              <div>
                <h2 className="text-lg font-semibold">{notification.title}</h2>
                <p className="text-sm text-gray-500">
                  {new Date(notification.timestamp).toLocaleString()}
                </p>
              </div>
            </div>
            <Button
              variant="ghost"
              size="sm"
              onClick={() => toggleRead(notification.id)}
              aria-label={notification.read ? "Mark as unread" : "Mark as read"}
            >
              <CheckCircle
                className={`h-5 w-5 ${notification.read ? "text-green-500" : "text-gray-300"}`}
              />
            </Button>
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
