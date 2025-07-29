import {
  BookOpen,
  Bot,
  Frame,
  Map,
  MessageSquareText,
  PieChart,
  Settings2,
  type LucideProps,
} from "lucide-react";
import * as React from "react";

import { NavMain } from "@/components/nav-main";
import { NavProjects } from "@/components/nav-projects";
import { NavUser } from "@/components/nav-user";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from "@/components/ui/sidebar";
import { getChats } from "@/pages/chat/api/ChatApi";
import { useQuery } from "@tanstack/react-query";
import { Link } from "react-router";

// This is sample data.
const menu = {
  user: {
    name: "shadcn",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  navMain: [
    {
      title: "Knowledge Base",
      url: "#",
      icon: BookOpen,
      items: [
        {
          title: "Browse Documents",
          url: "#",
        },
        {
          title: "Add Documents",
          url: "#",
        },
      ],
    },
  ],
};

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const [menuData, setMenuData] = React.useState(menu);

  const { isFetched, data } = useQuery({
    queryKey: ["conversations"],
    queryFn: () => getChats(),
  });

  React.useEffect(() => {
    const newObj = { ...menu };
    const conversationsObj: {
      title: string;
      url: string;
      icon: React.ForwardRefExoticComponent<
        Omit<LucideProps, "ref"> & React.RefAttributes<SVGSVGElement>
      >;
      isActive: boolean;
      items: {
        title: string;
        url: string;
      }[];
    } = {
      title: "Conversations",
      url: "/",
      icon: MessageSquareText,
      isActive: true,
      items: [],
    };
    if (!data) return;
    data.data.forEach((chat) => {
      const payload = {
        title: chat.title,
        url: `/?chat=${chat.id}`,
      };
      conversationsObj.items.push(payload);
    });
    newObj.navMain.splice(0, 0, conversationsObj);
    setMenuData(newObj);

    return () => {
      const newObj = { ...menu };
      newObj.navMain.splice(0, 1);
      setMenuData(newObj);
    };
  }, [data]);

  if (!isFetched) return "Loading...";

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <Link to="/">
          <b>Internal Knowledge base</b>
        </Link>
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={menuData.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={menu.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
