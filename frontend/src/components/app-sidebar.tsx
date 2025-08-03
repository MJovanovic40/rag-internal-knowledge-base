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
import { useAppSelector } from "@/hooks";
import type { UserState } from "@/pages/authentication/state/userSlice";
import { getChats, type ChatResponse } from "@/pages/chat/api/ChatApi";
import { setConversations } from "@/pages/chat/state/chatSlice";
import { useDispatch } from "react-redux";
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
  }[];
  projects: { name: string; url: string }[];
} = {
  navMain: [
    {
      title: "Knowledge Base",
      url: "/knowledge-base",
      icon: BookOpen,
    },
  ],
  projects: [],
};

const TEMPLATE_AVATAR_URL =
  "https://api.dicebear.com/9.x/initials/svg?seed={initials}&backgroundColor=262626";

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const dispatch = useDispatch();
  const user = useAppSelector((state) => state.user);
  const conversations = useAppSelector((state) => state.chat.conversations);

  const getInitials = (name: string) => {
    if (name.length === 0) return "U";

    const components = name.split(" ");

    if (components.length === 1) return Array.from(name)[0];

    components.splice(2);

    return Array.from(components[0])[0] + Array.from(components[1])[0];
  };

  const convertUser = (user: UserState): UserDetails => {
    return {
      name: user.name,
      email: user.email,
      avatar: TEMPLATE_AVATAR_URL.replace("{initials}", getInitials(user.name)),
    };
  };

  const getConversations = async () => {
    const resp = await getChats();
    dispatch(setConversations(resp));
    return resp;
  };

  const getConversationsObj = (chatResponse: ChatResponse[]) => {
    const objs: { name: string; url: string }[] = [];

    chatResponse.forEach((conversation) => {
      objs.push({
        name: conversation.title,
        url: `/?chat=${conversation.id}`,
      });
    });
    return objs;
  };

  const userObj = convertUser(user);
  const conversationsObj = getConversationsObj(conversations);

  React.useEffect(() => {
    getConversations();
  }, []);

  // if (!isFetched) return "Loading...";

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <Link to="/">
          <b>IKB</b>
        </Link>
      </SidebarHeader>
      <SidebarContent>
        <NavMain items={menu.navMain} />
        <NavProjects projects={conversationsObj} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={userObj} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
