"use client";

import { useState } from "react";
import EmptyState from "./_components/empty-state";
import Header from "./_components/header";
import NotificationList from "./_components/notification-list";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { createListNotificationsQueryOptions } from "@/lib/apis/(notification)/list-notifications.api";
import { NotificationSchema } from "@/lib/schemas/(notification)/notification.schema";

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
  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions()
  );

  const listNotificationsQuery = useQuery(
    createListNotificationsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    })
  );

  const notifications = listNotificationsQuery.data?.content ?? [];

  return (
    <div className="container mx-auto px-4 py-8">
      <Header />
      {notifications.length > 0 ? (
        <NotificationList notifications={notifications} />
      ) : (
        <EmptyState />
      )}
    </div>
  );
}
