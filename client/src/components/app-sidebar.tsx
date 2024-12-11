"use client";

import * as React from "react";
import {
  BookOpen,
  Bot,
  Frame,
  GalleryVerticalEnd,
  Map,
  PieChart,
  SquareTerminal,
} from "lucide-react";

import { NavMain } from "@/components/nav-main";
import { NavUser } from "@/components/nav-user";
import { TeamSwitcher } from "@/components/team-switcher";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { useQuery } from "@tanstack/react-query";

const data = {
  user: {
    name: "shadcn",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  teams: [
    {
      name: "Medical Diary",
      logo: GalleryVerticalEnd,
      plan: "Powered by Anonymous",
    },
  ],
  navMain: [
    {
      title: "Personal Information",
      url: "#",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "Profile",
          url: "/profile",
        },
        {
          title: "Household",
          url: "/households",
        },
        {
          title: "Notification",
          url: "/notifications",
        },
      ],
    },
    {
      title: "Medical Records",
      url: "#",
      icon: Bot,
      items: [
        {
          title: "Medical History",
          url: "/medical-history",
        },
        {
          title: "Prescription",
          url: "/prescriptions",
        },
        {
          title: "Physician Notes",
          url: "/physician-notes",
        },
        {
          title: "Surgeries",
          url: "/surgeries",
        },
        {
          title: "Vaccination",
          url: "/vacinations",
        },
      ],
    },
    {
      title: "Health Status",
      url: "#",
      icon: BookOpen,
      items: [
        {
          title: "Past Conditions",
          url: "/past-conditions",
        },
        {
          title: "Allergies",
          url: "/allergies",
        },
        {
          title: "Family History",
          url: "/family-history",
        },
        {
          title: "Diagnostic Reports",
          url: "/diagnostic-reports",
        },
      ],
    },
  ],

  projects: [
    {
      name: "Design Engineering",
      url: "#",
      icon: Frame,
    },
    {
      name: "Sales & Marketing",
      url: "#",
      icon: PieChart,
    },
    {
      name: "Travel",
      url: "#",
      icon: Map,
    },
  ],
};

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const getCurrentUserInformationQuery = useQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const user = {
    name: getCurrentUserInformationQuery.data?.name || "unknown",
    email: getCurrentUserInformationQuery.data?.email || "unknown",
    avatar:
      getCurrentUserInformationQuery.data?.avatarUrl || "/avatars/unknown.jpg",
  };

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMain} />
        {/*<NavProjects projects={data.projects} />*/}
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
