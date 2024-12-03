"use client";

import { useState } from "react";
import EmptyState from "./_components/empty-state";
import Header from "./_components/header";
import NotificationList from "./_components/notification-list";

interface Notification {
  id: string;
  title: string;
  timestamp: string;
  read: boolean;
}

const initialNotifications: Notification[] = [
  {
    id: "1",
    title: "New message from Dr. Smith",
    timestamp: "2023-06-15T10:30:00Z",
    read: false,
  },
  {
    id: "2",
    title: "Appointment reminder",
    timestamp: "2023-06-14T15:45:00Z",
    read: true,
  },
  {
    id: "3",
    title: "Lab results available",
    timestamp: "2023-06-13T09:00:00Z",
    read: false,
  },
];

export default function NotificationsPage() {
  const [notifications, setNotifications] =
    useState<Notification[]>(initialNotifications);

  const toggleRead = (id: string) => {
    setNotifications(
      notifications.map((notification) =>
        notification.id === id
          ? { ...notification, read: !notification.read }
          : notification,
      ),
    );
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <Header />
      {notifications.length > 0 ? (
        <NotificationList
          notifications={notifications}
          toggleRead={toggleRead}
        />
      ) : (
        <EmptyState />
      )}
    </div>
  );
}
