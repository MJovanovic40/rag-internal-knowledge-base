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
import { useAppSelector } from "@/hooks";
import { setCurrentChat } from "@/pages/chat/state/chatSlice";
import { Separator } from "@radix-ui/react-separator";
import { useEffect, useState, type JSX } from "react";
import { useDispatch } from "react-redux";
import { useLocation } from "react-router";

type ComponentInfo = {
  pageComponent: JSX.Element;
  showSidebar: boolean;
};

export default function Layout(props: ComponentInfo) {
  const [currentLocation, setCurrentLocation] = useState<string>();

  const location = useLocation();

  const currentChat = useAppSelector((state) => state.chat.current);
  const dispatch = useDispatch();

  useEffect(() => {
    if (location == null) return;

    let section = "";

    switch (location.pathname) {
      case "/":
        section = "Chat";
        break;
      case "/knowledge-base":
        section = "Knowledge Base";
        dispatch(setCurrentChat({ id: "", title: "" }));
        break;
    }
    setCurrentLocation(section);
  }, [location]);

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
                {currentChat.title && (
                  <>
                    <BreadcrumbSeparator className="hidden md:block" />
                    <BreadcrumbItem>
                      <BreadcrumbPage>{currentChat.title}</BreadcrumbPage>
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
