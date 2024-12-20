import { Card, CardContent } from "@/components/ui/card";
import { Bell, CheckCircle } from "lucide-react";
import { Button } from "@/components/ui/button";
import { NotificationSchema } from "@/lib/schemas/(notification)/notification.schema";
import { useUpdateStatusNotificationMutation } from "@/lib/apis/(notification)/update-status-notifications.api";
import { useToast } from "@/hooks/use-toast";

interface Notification {
  id: string;
  title: string;
  timestamp: string;
  read: boolean;
}

interface NotificationListProps {
  notifications: NotificationSchema[];
}

type NotificationStatusButtonProps = {
  id: string;
  status?: boolean;
};

export function NotificationStatusButton({
  id,
  status,
}: NotificationStatusButtonProps) {
  const { toast } = useToast();

  const mutation = useUpdateStatusNotificationMutation({ Id: id });

  const handleToggleStatus = async () => {
    await mutation.mutateAsync({ readStatus: !status });
    toast({
      title: `Notification marked as ${!status ? "read" : "unread"}`,
    });
  };

  return (
    <Button
      variant="ghost"
      size="sm"
      aria-label={status ? "Mark as unread" : "Mark as read"}
      onClick={handleToggleStatus}
    >
      <CheckCircle
        className={`h-5 w-5 ${status ? "text-green-500" : "text-gray-300"}`}
      />
    </Button>
  );
}

export default function NotificationList({
  notifications,
}: NotificationListProps) {
  return (
    <div className="space-y-4">
      {notifications.map((notification) => (
        <Card
          key={notification.id}
          className={notification.readStatus ? "bg-gray-100" : "bg-white"}
        >
          <CardContent className="flex items-center justify-between p-4">
            <div className="flex items-center space-x-4">
              <div
                className={`p-2 rounded-full ${
                  notification.readStatus ? "bg-gray-300" : "bg-blue-500"
                }`}
              >
                <Bell
                  className={`h-5 w-5 ${
                    notification.readStatus ? "text-gray-600" : "text-white"
                  }`}
                />
              </div>
              <div>
                <h2 className="text-lg font-semibold">{notification.title}</h2>
                <p className="text-sm text-gray-500">
                  {new Date(notification.notificationDate).toLocaleString()}
                </p>
              </div>
            </div>

            <NotificationStatusButton
              id={notification.id}
              status={notification.readStatus}
            />
          </CardContent>
        </Card>
      ))}
    </div>
  );
}
