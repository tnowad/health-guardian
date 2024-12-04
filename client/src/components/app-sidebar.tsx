"use client";

import * as React from "react";
import {
  AudioWaveform,
  BookOpen,
  Bot,
  Command,
  Frame,
  GalleryVerticalEnd,
  Map,
  PieChart,
  Settings2,
  SquareTerminal,
} from "lucide-react";

import { NavMain } from "@/components/nav-main";
import { NavProjects } from "@/components/nav-projects";
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

// This is sample data.
const data = {
  user: {
    name: "shadcn",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  teams: [
    {
      name: "Patient Sidebar",
      logo: GalleryVerticalEnd,
      plan: "Enterprise",
    },
    {
      name: "Staff Sidebar",
      logo: AudioWaveform,
      plan: "Startup",
    },
    {
      name: "Medical Staff Sidebar",
      logo: Command,
      plan: "Free",
    },
  ],
  navMainPatient: [
    {
      title: "Dashboard",
      url: "#",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "Overview",
          url: "#",
        },
      ],
    },
    {
      title: "Profile",
      url: "#",
      icon: Bot,
      items: [
        {
          title: "Personal Details",
          url: "/profile",
        },
        {
          title: "Medical Status",
          url: "#",
        },
      ],
    },
    {
      title: "Health",
      url: "#",
      icon: BookOpen,
      items: [
        {
          title: "Appointments",
          url: "#",
        },
        {
          title: "Medications",
          url: "#",
        },
        {
          title: "Consent Forms",
          url: "#",
        },
      ],
    },
    {
      title: "Logs and Notifications",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Health logs",
          url: "#",
        },
        {
          title: "Notifications",
          url: "#",
        },
      ],
    },
    {
      title: "Support",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Guardian Contact",
          url: "#",
        },
        {
          title: "Assigned Medical Staff",
          url: "#",
        },
      ],
    },
  ],
  navMainStaff: [
    {
      title: "Dashboard",
      url: "#",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "Patient Overview",
          url: "#",
        },
        {
          title: "System Status",
          url: "#",
        },
      ],
    },
    {
      title: "Patient Management",
      url: "#",
      icon: Bot,
      items: [
        {
          title: "Patients",
          url: "#",
        },
        {
          title: "Appointments",
          url: "#",
        },
      ],
    },
    {
      title: "System Management",
      url: "#",
      icon: BookOpen,
      items: [
        {
          title: "Logs",
          url: "#",
        },
        {
          title: "Maintenance",
          url: "#",
        },
      ],
    },
    {
      title: "Notifications",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Unread Notifications",
          url: "#",
        },
        {
          title: "All Notifications",
          url: "#",
        },
      ],
    },
    {
      title: "Support",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Medical Staff Directory",
          url: "#",
        },
        {
          title: "Guardian Directory",
          url: "#",
        },
      ],
    },
  ],
  navMainMedicalStaff: [
    {
      title: "Dashboard",
      url: "#",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "My Schedule",
          url: "#",
        },
        {
          title: "Patient Activity",
          url: "#",
        },
      ],
    },
    {
      title: "Patient Management",
      url: "#",
      icon: Bot,
      items: [
        {
          title: "Patient Profiles",
          url: "#",
        },
        {
          title: "Medical Records",
          url: "#",
        },
        {
          title: "Logs",
          url: "#",
        },
      ],
    },
    {
      title: "Appointments",
      url: "#",
      icon: BookOpen,
      items: [
        {
          title: "My Appointments",
          url: "#",
        },
        {
          title: "All Appointments",
          url: "#",
        },
        {
          title: "Appointment History",
          url: "#",
        },
      ],
    },
    {
      title: "Prescriptions and Medications",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Current Prescriptions",
          url: "#",
        },
        {
          title: "Prescription History",
          url: "#",
        },
        {
          title: "Side Effects",
          url: "#",
        },
      ],
    },
    {
      title: "Notifications",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Unread Notifications",
          url: "#",
        },
        {
          title: "All Notifications",
          url: "#",
        },
      ],
    },
    {
      title: "Staff Management",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "Medical Staff Directory",
          url: "#",
        },
        {
          title: "Support Resources",
          url: "#",
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

  return (
    // <Sidebar collapsible="icon" {...props}>
    //   <SidebarHeader>
    //     <TeamSwitcher teams={data.teams} />
    //   </SidebarHeader>
    //   <SidebarContent>
    //     <NavMain items={data.navMain} />
    //     <NavProjects projects={data.projects} />
    //   </SidebarContent>
    //   <SidebarFooter>
    //     <NavUser user={data.user} />
    //   </SidebarFooter>
    //   <SidebarRail />
    // </Sidebar>

    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <TeamSwitcher teams={data.teams} />
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={data.navMainPatient} />
        {/*<NavProjects projects={data.projects} />*/}
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>

    // <Sidebar collapsible="icon" {...props}>
    //   <SidebarHeader>
    //     <TeamSwitcher teams={data.teams} />
    //   </SidebarHeader>
    //   <SidebarContent>
    //     <NavMain items={data.navMainStaff} />
    //     {/*<NavProjects projects={data.projects} />*/}
    //   </SidebarContent>
    //   <SidebarFooter>
    //     <NavUser user={data.user} />
    //   </SidebarFooter>
    //   <SidebarRail />
    // </Sidebar>

    // <Sidebar collapsible="icon" {...props}>
    //   <SidebarHeader>
    //     <TeamSwitcher teams={data.teams} />
    //   </SidebarHeader>
    //   <SidebarContent>
    //     <NavMain items={data.navMainMedicalStaff} />
    //     {/*<NavProjects projects={data.projects} />*/}
    //   </SidebarContent>
    //   <SidebarFooter>
    //     <NavUser user={data.user} />
    //   </SidebarFooter>
    //   <SidebarRail />
    // </Sidebar>
  );
}
