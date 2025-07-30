import { BookOpen, type LucideIcon } from "lucide-react";
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
import { getUserDetails } from "@/pages/authentication/api/UserApi";
import { getChats } from "@/pages/chat/api/ChatApi";
import { useQuery } from "@tanstack/react-query";
import { Link } from "react-router";

type UserDetails = {
  name: string;
  email: string;
  avatar: string;
};

// This is sample data.
const menu: {
  navMain: {
    title: string;
    url: string;
    icon: LucideIcon;
    items: { title: string; url: string }[];
  }[];
  projects: { name: string; url: string }[];
} = {
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
  projects: [],
};

const TEMPLATE_AVATAR_URL =
  "https://api.dicebear.com/9.x/initials/svg?seed={initials}&backgroundColor=262626";

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const [conversations, setConversations] = React.useState<{ name: string; url: string }[]>([]);
  const [user, setUser] = React.useState<UserDetails>({
    name: "shadcn",
    email: "m@example.com",
    avatar: "https://api.dicebear.com/9.x/initials/svg?seed=SH&backgroundColor=262626",
  });

  const { isFetched, data } = useQuery({
    queryKey: ["conversations"],
    queryFn: () => getChats(),
    refetchInterval: 3000,
  });

  const getInitials = (name: string) => {
    if (name.length === 0) return "U";

    const components = name.split(" ");

    if (components.length === 1) return Array.from(name)[0];

    components.splice(2);

    return Array.from(components[0])[0] + Array.from(components[1])[0];
  };

  const getUser = async () => {
    const user = (await getUserDetails()).data;
    const initials = getInitials(user.name);

    const userDetails: UserDetails = {
      name: user.name,
      email: user.email,
      avatar: TEMPLATE_AVATAR_URL.replace("{initials}", initials),
    };

    setUser(userDetails);
  };

  React.useEffect(() => {
    getUser();
  }, []);

  React.useEffect(() => {
    if (!data) return;

    const conversations: { name: string; url: string }[] = [];

    data.data.forEach((chat) => {
      const payload = {
        name: chat.title,
        url: `/?chat=${chat.id}`,
      };
      conversations.push(payload);
    });

    setConversations(conversations);

    return () => {
      setConversations([]);
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
        <NavMain items={menu.navMain} />
        <NavProjects projects={conversations} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
