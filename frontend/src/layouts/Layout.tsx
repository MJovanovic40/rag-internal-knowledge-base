import { AppSidebar } from "@/components/app-sidebar";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { getChat, type ChatResponse } from "@/pages/chat/api/ChatApi";
import { Separator } from "@radix-ui/react-separator";
import { useEffect, useState, type JSX } from "react";
import { useLocation, useSearchParams } from "react-router";

type ComponentInfo = {
  pageComponent: JSX.Element;
  showSidebar: boolean;
};

export default function Layout(props: ComponentInfo) {
  const [currentLocation, setCurrentLocation] = useState<string>();
  const [currentSublocation, setCurrentSublocation] = useState<string>();
  const [currentChat, setCurrentChat] = useState<ChatResponse | null>();

  const location = useLocation();
  const [searchParams] = useSearchParams();

  const getCurrentChat = async (chatId: string) => {
    setCurrentChat((await getChat(chatId)).data);
  };

  useEffect(() => {
    if (location == null) return;

    let section = "";

    switch (location.pathname) {
      case "/":
        section = "Chat";

        break;
    }
    setCurrentLocation(section);
  }, [location]);

  useEffect(() => {
    if (searchParams == null) return;

    const chatId = searchParams.get("chat");

    if (chatId) getCurrentChat(chatId);
    else setCurrentSublocation("New Chat");
  }, [searchParams]);

  useEffect(() => {
    if (currentLocation == "Chat") {
      if (currentChat == null) {
        setCurrentSublocation("New Chat");
        return;
      }
      setCurrentSublocation(currentChat.title);
    }
  }, [currentChat, currentLocation]);

  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset>
        <header className="fixed flex h-16 shrink-0 items-center gap-2 transition-[width,height] ease-linear group-has-data-[collapsible=icon]/sidebar-wrapper:h-12 bg-neutral-900 w-full">
          <div className="flex items-center gap-2 px-4 ">
            <SidebarTrigger className="-ml-1" />
            <Separator orientation="vertical" className="mr-2 data-[orientation=vertical]:h-4" />
            <Breadcrumb>
              <BreadcrumbList>
                <BreadcrumbItem className="hidden md:block">
                  <BreadcrumbLink href="#">{currentLocation}</BreadcrumbLink>
                </BreadcrumbItem>
                {currentSublocation && (
                  <>
                    <BreadcrumbSeparator className="hidden md:block" />
                    <BreadcrumbItem>
                      <BreadcrumbPage>{currentSublocation}</BreadcrumbPage>
                    </BreadcrumbItem>
                  </>
                )}
              </BreadcrumbList>
            </Breadcrumb>
          </div>
        </header>
        <div className="flex flex-1 justify-center bg-neutral-900">{props.pageComponent}</div>
      </SidebarInset>
    </SidebarProvider>
  );
}
