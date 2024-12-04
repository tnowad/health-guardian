"use client";
import Image from "next/image";
import { File, ListFilter, MoreHorizontal, PlusCircle } from "lucide-react";

import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useRouter } from "next/navigation";

export default function Page() {
  const router = useRouter();
  router.push("/profile");
  return null

  // return (
  //   <Tabs defaultValue="all">
  //     <div className="flex items-center">
  //       <TabsList>
  //         <TabsTrigger value="all">All</TabsTrigger>
  //         <TabsTrigger value="active">Active</TabsTrigger>
  //         <TabsTrigger value="draft">Draft</TabsTrigger>
  //         <TabsTrigger value="archived" className="hidden sm:flex">
  //           Archived
  //         </TabsTrigger>
  //       </TabsList>
  //       <div className="ml-auto flex items-center gap-2">
  //         <DropdownMenu>
  //           <DropdownMenuTrigger asChild>
  //             <Button variant="outline" size="sm" className="h-8 gap-1">
  //               <ListFilter className="h-3.5 w-3.5" />
  //               <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
  //                 Filter
  //               </span>
  //             </Button>
  //           </DropdownMenuTrigger>
  //           <DropdownMenuContent align="end">
  //             <DropdownMenuLabel>Filter by</DropdownMenuLabel>
  //             <DropdownMenuSeparator />
  //             <DropdownMenuCheckboxItem checked>
  //               Active
  //             </DropdownMenuCheckboxItem>
  //             <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
  //             <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
  //           </DropdownMenuContent>
  //         </DropdownMenu>
  //         <Button size="sm" variant="outline" className="h-8 gap-1">
  //           <File className="h-3.5 w-3.5" />
  //           <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
  //             Export
  //           </span>
  //         </Button>
  //         <Button size="sm" className="h-8 gap-1">
  //           <PlusCircle className="h-3.5 w-3.5" />
  //           <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
  //             Add Product
  //           </span>
  //         </Button>
  //       </div>
  //     </div>
  //     <TabsContent value="all">
  //       <Card x-chunk="dashboard-06-chunk-0">
  //         <CardHeader>
  //           <CardTitle>Products</CardTitle>
  //           <CardDescription>
  //             Manage your products and view their sales performance.
  //           </CardDescription>
  //         </CardHeader>
  //         <CardContent>
  //           <Table>
  //             <TableHeader>
  //               <TableRow>
  //                 <TableHead className="hidden w-[100px] sm:table-cell">
  //                   <span className="sr-only">Image</span>
  //                 </TableHead>
  //                 <TableHead>Name</TableHead>
  //                 <TableHead>Status</TableHead>
  //                 <TableHead className="hidden md:table-cell">Price</TableHead>
  //                 <TableHead className="hidden md:table-cell">
  //                   Total Sales
  //                 </TableHead>
  //                 <TableHead className="hidden md:table-cell">
  //                   Created at
  //                 </TableHead>
  //                 <TableHead>
  //                   <span className="sr-only">Actions</span>
  //                 </TableHead>
  //               </TableRow>
  //             </TableHeader>
  //             <TableBody>
  //               <TableRow>
  //                 <TableCell className="hidden sm:table-cell">
  //                   <Image
  //                     alt="Product image"
  //                     className="aspect-square rounded-md object-cover"
  //                     height="64"
  //                     src="/placeholder.svg"
  //                     width="64"
  //                   />
  //                 </TableCell>
  //                 <TableCell className="font-medium">
  //                   Laser Lemonade Machine
  //                 </TableCell>
  //                 <TableCell>
  //                   <Badge variant="outline">Draft</Badge>
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   $499.99
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">25</TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   2023-07-12 10:42 AM
  //                 </TableCell>
  //                 <TableCell>
  //                   <DropdownMenu>
  //                     <DropdownMenuTrigger asChild>
  //                       <Button
  //                         aria-haspopup="true"
  //                         size="icon"
  //                         variant="ghost"
  //                       >
  //                         <MoreHorizontal className="h-4 w-4" />
  //                         <span className="sr-only">Toggle menu</span>
  //                       </Button>
  //                     </DropdownMenuTrigger>
  //                     <DropdownMenuContent align="end">
  //                       <DropdownMenuLabel>Actions</DropdownMenuLabel>
  //                       <DropdownMenuItem>Edit</DropdownMenuItem>
  //                       <DropdownMenuItem>Delete</DropdownMenuItem>
  //                     </DropdownMenuContent>
  //                   </DropdownMenu>
  //                 </TableCell>
  //               </TableRow>
  //               <TableRow>
  //                 <TableCell className="hidden sm:table-cell">
  //                   <Image
  //                     alt="Product image"
  //                     className="aspect-square rounded-md object-cover"
  //                     height="64"
  //                     src="/placeholder.svg"
  //                     width="64"
  //                   />
  //                 </TableCell>
  //                 <TableCell className="font-medium">
  //                   Hypernova Headphones
  //                 </TableCell>
  //                 <TableCell>
  //                   <Badge variant="outline">Active</Badge>
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   $129.99
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">100</TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   2023-10-18 03:21 PM
  //                 </TableCell>
  //                 <TableCell>
  //                   <DropdownMenu>
  //                     <DropdownMenuTrigger asChild>
  //                       <Button
  //                         aria-haspopup="true"
  //                         size="icon"
  //                         variant="ghost"
  //                       >
  //                         <MoreHorizontal className="h-4 w-4" />
  //                         <span className="sr-only">Toggle menu</span>
  //                       </Button>
  //                     </DropdownMenuTrigger>
  //                     <DropdownMenuContent align="end">
  //                       <DropdownMenuLabel>Actions</DropdownMenuLabel>
  //                       <DropdownMenuItem>Edit</DropdownMenuItem>
  //                       <DropdownMenuItem>Delete</DropdownMenuItem>
  //                     </DropdownMenuContent>
  //                   </DropdownMenu>
  //                 </TableCell>
  //               </TableRow>
  //               <TableRow>
  //                 <TableCell className="hidden sm:table-cell">
  //                   <Image
  //                     alt="Product image"
  //                     className="aspect-square rounded-md object-cover"
  //                     height="64"
  //                     src="/placeholder.svg"
  //                     width="64"
  //                   />
  //                 </TableCell>
  //                 <TableCell className="font-medium">
  //                   AeroGlow Desk Lamp
  //                 </TableCell>
  //                 <TableCell>
  //                   <Badge variant="outline">Active</Badge>
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">$39.99</TableCell>
  //                 <TableCell className="hidden md:table-cell">50</TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   2023-11-29 08:15 AM
  //                 </TableCell>
  //                 <TableCell>
  //                   <DropdownMenu>
  //                     <DropdownMenuTrigger asChild>
  //                       <Button
  //                         aria-haspopup="true"
  //                         size="icon"
  //                         variant="ghost"
  //                       >
  //                         <MoreHorizontal className="h-4 w-4" />
  //                         <span className="sr-only">Toggle menu</span>
  //                       </Button>
  //                     </DropdownMenuTrigger>
  //                     <DropdownMenuContent align="end">
  //                       <DropdownMenuLabel>Actions</DropdownMenuLabel>
  //                       <DropdownMenuItem>Edit</DropdownMenuItem>
  //                       <DropdownMenuItem>Delete</DropdownMenuItem>
  //                     </DropdownMenuContent>
  //                   </DropdownMenu>
  //                 </TableCell>
  //               </TableRow>
  //               <TableRow>
  //                 <TableCell className="hidden sm:table-cell">
  //                   <Image
  //                     alt="Product image"
  //                     className="aspect-square rounded-md object-cover"
  //                     height="64"
  //                     src="/placeholder.svg"
  //                     width="64"
  //                   />
  //                 </TableCell>
  //                 <TableCell className="font-medium">
  //                   TechTonic Energy Drink
  //                 </TableCell>
  //                 <TableCell>
  //                   <Badge variant="secondary">Draft</Badge>
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">$2.99</TableCell>
  //                 <TableCell className="hidden md:table-cell">0</TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   2023-12-25 11:59 PM
  //                 </TableCell>
  //                 <TableCell>
  //                   <DropdownMenu>
  //                     <DropdownMenuTrigger asChild>
  //                       <Button
  //                         aria-haspopup="true"
  //                         size="icon"
  //                         variant="ghost"
  //                       >
  //                         <MoreHorizontal className="h-4 w-4" />
  //                         <span className="sr-only">Toggle menu</span>
  //                       </Button>
  //                     </DropdownMenuTrigger>
  //                     <DropdownMenuContent align="end">
  //                       <DropdownMenuLabel>Actions</DropdownMenuLabel>
  //                       <DropdownMenuItem>Edit</DropdownMenuItem>
  //                       <DropdownMenuItem>Delete</DropdownMenuItem>
  //                     </DropdownMenuContent>
  //                   </DropdownMenu>
  //                 </TableCell>
  //               </TableRow>
  //               <TableRow>
  //                 <TableCell className="hidden sm:table-cell">
  //                   <Image
  //                     alt="Product image"
  //                     className="aspect-square rounded-md object-cover"
  //                     height="64"
  //                     src="/placeholder.svg"
  //                     width="64"
  //                   />
  //                 </TableCell>
  //                 <TableCell className="font-medium">
  //                   Gamer Gear Pro Controller
  //                 </TableCell>
  //                 <TableCell>
  //                   <Badge variant="outline">Active</Badge>
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">$59.99</TableCell>
  //                 <TableCell className="hidden md:table-cell">75</TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   2024-01-01 12:00 AM
  //                 </TableCell>
  //                 <TableCell>
  //                   <DropdownMenu>
  //                     <DropdownMenuTrigger asChild>
  //                       <Button
  //                         aria-haspopup="true"
  //                         size="icon"
  //                         variant="ghost"
  //                       >
  //                         <MoreHorizontal className="h-4 w-4" />
  //                         <span className="sr-only">Toggle menu</span>
  //                       </Button>
  //                     </DropdownMenuTrigger>
  //                     <DropdownMenuContent align="end">
  //                       <DropdownMenuLabel>Actions</DropdownMenuLabel>
  //                       <DropdownMenuItem>Edit</DropdownMenuItem>
  //                       <DropdownMenuItem>Delete</DropdownMenuItem>
  //                     </DropdownMenuContent>
  //                   </DropdownMenu>
  //                 </TableCell>
  //               </TableRow>
  //               <TableRow>
  //                 <TableCell className="hidden sm:table-cell">
  //                   <Image
  //                     alt="Product image"
  //                     className="aspect-square rounded-md object-cover"
  //                     height="64"
  //                     src="/placeholder.svg"
  //                     width="64"
  //                   />
  //                 </TableCell>
  //                 <TableCell className="font-medium">
  //                   Luminous VR Headset
  //                 </TableCell>
  //                 <TableCell>
  //                   <Badge variant="outline">Active</Badge>
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   $199.99
  //                 </TableCell>
  //                 <TableCell className="hidden md:table-cell">30</TableCell>
  //                 <TableCell className="hidden md:table-cell">
  //                   2024-02-14 02:14 PM
  //                 </TableCell>
  //                 <TableCell>
  //                   <DropdownMenu>
  //                     <DropdownMenuTrigger asChild>
  //                       <Button
  //                         aria-haspopup="true"
  //                         size="icon"
  //                         variant="ghost"
  //                       >
  //                         <MoreHorizontal className="h-4 w-4" />
  //                         <span className="sr-only">Toggle menu</span>
  //                       </Button>
  //                     </DropdownMenuTrigger>
  //                     <DropdownMenuContent align="end">
  //                       <DropdownMenuLabel>Actions</DropdownMenuLabel>
  //                       <DropdownMenuItem>Edit</DropdownMenuItem>
  //                       <DropdownMenuItem>Delete</DropdownMenuItem>
  //                     </DropdownMenuContent>
  //                   </DropdownMenu>
  //                 </TableCell>
  //               </TableRow>
  //             </TableBody>
  //           </Table>
  //         </CardContent>
  //         <CardFooter>
  //           <div className="text-xs text-muted-foreground">
  //             Showing <strong>1-10</strong> of <strong>32</strong> products
  //           </div>
  //         </CardFooter>
  //       </Card>
  //     </TabsContent>
  //   </Tabs>

    //Patient
    //Health->Appointments
    // <Tabs defaultValue="Upcoming Appointments">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Upcoming Appointments">Upcoming Appointments</TabsTrigger>
    //       <TabsTrigger value="Schedule Appointment">Schedule Appointment</TabsTrigger>
    //       <TabsTrigger value="History" className="hidden sm:flex">History</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Upcoming Appointments">
    //     Upcoming Appointments
    //   </TabsContent>
    //   <TabsContent value="Schedule Appointment">
    //     Schedule Appointment
    //   </TabsContent>
    //   <TabsContent value="History">
    //     History
    //   </TabsContent>
    // </Tabs>
    // );

    //Patient
    //Health->Medications
    // <Tabs defaultValue="Current Prescriptions">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Current Prescriptions">Current Prescriptions</TabsTrigger>
    //       <TabsTrigger value="Prescription History">Prescription History</TabsTrigger>
    //       <TabsTrigger value="Side Effects" className="hidden sm:flex">Side Effects</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Current Prescriptions">
    //     Current Prescriptions
    //   </TabsContent>
    //   <TabsContent value="Prescription History">
    //     Prescription History
    //   </TabsContent>
    //   <TabsContent value="Side Effects">
    //     Side Effects
    //   </TabsContent>
    // </Tabs>

    //Patient
    //Health->Medications
    // <Tabs defaultValue="Unread Notifications">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Unread Notifications">Unread Notifications</TabsTrigger>
    //       <TabsTrigger value="All Notifications" className="hidden sm:flex">All Notifications</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Unread Notifications">
    //     Unread Notifications
    //   </TabsContent>
    //   <TabsContent value="All Notifications">
    //     All Notifications
    //   </TabsContent>
    // </Tabs>

    //Staff Sidebar
    //Patient Management->Patients
    // <Tabs defaultValue="Search Patient Profiles">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Search Patient Profiles">Search Patient Profiles</TabsTrigger>
    //       <TabsTrigger value="Households">Households</TabsTrigger>
    //       <TabsTrigger value="Guardians" className="hidden sm:flex">Guardians</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Search Patient Profiles">
    //     Search Patient Profiles
    //   </TabsContent>
    //   <TabsContent value="Households">
    //     Households
    //   </TabsContent>
    //   <TabsContent value="Guardians">
    //     Guardians
    //   </TabsContent>
    // </Tabs>

    //Staff Sidebar
    //Patient Management->Appointments
    // <Tabs defaultValue="Upcoming Appointments">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Upcoming Appointments">Upcoming Appointments</TabsTrigger>
    //       <TabsTrigger value="Appointment History" className="hidden sm:flex">Appointment History</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Upcoming Appointments">
    //     Upcoming Appointments
    //   </TabsContent>
    //   <TabsContent value="Appointment History">
    //     Appointment History
    //   </TabsContent>
    // </Tabs>

    //Staff Sidebar
    //System Management->Logs
    // <Tabs defaultValue="Patient Logs">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Patient Logs">Patient Logs</TabsTrigger>
    //       <TabsTrigger value="System Logs" className="hidden sm:flex">System Logs</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Patient Logs">
    //     Patient Logs
    //   </TabsContent>
    //   <TabsContent value="System Logs">
    //     System Logs
    //   </TabsContent>
    // </Tabs>

    //Staff Sidebar
    //System Management->Maintenance
    // <Tabs defaultValue="System Backups">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="System Backups">System Backups</TabsTrigger>
    //       <TabsTrigger value="Scheduled Maintenance" className="hidden sm:flex">Scheduled Maintenance</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="System Backups">
    //     System Backups
    //   </TabsContent>
    //   <TabsContent value="Scheduled Maintenance">
    //     Scheduled Maintenance
    //   </TabsContent>
    // </Tabs>

    //Medical Staff Sidebar
    //Patient Management->Patient Profiles
    // <Tabs defaultValue="Search and Access Profiles">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Search and Access Profiles">Search and Access Profiles</TabsTrigger>
    //       <TabsTrigger value="Household and Guardians" className="hidden sm:flex">Household and Guardians</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Search and Access Profiles">
    //     Search and Access Profiles
    //   </TabsContent>
    //   <TabsContent value="Household and Guardians">
    //     Household and Guardians
    //   </TabsContent>
    // </Tabs>

    //Medical Staff Sidebar
    //Patient Management->Medical Records
    // <Tabs defaultValue="Medical History">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Medical History">Medical History</TabsTrigger>
    //       <TabsTrigger value="Consent Forms" className="hidden sm:flex">Consent Forms</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Medical History">
    //     Medical History
    //   </TabsContent>
    //   <TabsContent value="Consent Forms">
    //     Consent Forms
    //   </TabsContent>
    // </Tabs>

    //Medical Staff Sidebar
    //Patient Management->Logs
    // <Tabs defaultValue="Patient Logs">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Patient Logs">Patient Logs</TabsTrigger>
    //       <TabsTrigger value="System Logs" className="hidden sm:flex">System Logs</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Patient Logs">
    //     Patient Logs
    //   </TabsContent>
    //   <TabsContent value="System Logs">
    //     System Logs
    //   </TabsContent>
    // </Tabs>

    //Medical Staff Sidebar
    //Prescriptions and Medications->Current Prescriptions
    // <Tabs defaultValue="Prescribe Medication">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Prescribe Medication">Prescribe Medication</TabsTrigger>
    //       <TabsTrigger value="Update Dosage" className="hidden sm:flex">Update Dosage</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Prescribe Medication">
    //     Prescribe Medication
    //   </TabsContent>
    //   <TabsContent value="Update Dosage">
    //     Update Dosage
    //   </TabsContent>
    // </Tabs>

    //Medical Staff Sidebar
    //Prescriptions and Medications->Side Effects
    // <Tabs defaultValue="Reported Side Effects">
    //   <div className="flex items-center">
    //     <TabsList>
    //       <TabsTrigger value="Reported Side Effects">Reported Side Effects</TabsTrigger>
    //       <TabsTrigger value="Aggregated Data" className="hidden sm:flex">Aggregated Data</TabsTrigger>
    //     </TabsList>
    //     <div className="ml-auto flex items-center gap-2">
    //       <DropdownMenu>
    //         <DropdownMenuTrigger asChild>
    //           <Button variant="outline" size="sm" className="h-8 gap-1">
    //             <ListFilter className="h-3.5 w-3.5" />
    //             <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //                 Filter
    //               </span>
    //           </Button>
    //         </DropdownMenuTrigger>
    //         <DropdownMenuContent align="end">
    //           <DropdownMenuLabel>Filter by</DropdownMenuLabel>
    //           <DropdownMenuSeparator />
    //           <DropdownMenuCheckboxItem checked>
    //             Active
    //           </DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Draft</DropdownMenuCheckboxItem>
    //           <DropdownMenuCheckboxItem>Archived</DropdownMenuCheckboxItem>
    //         </DropdownMenuContent>
    //       </DropdownMenu>
    //       <Button size="sm" variant="outline" className="h-8 gap-1">
    //         <File className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Export
    //           </span>
    //       </Button>
    //       <Button size="sm" className="h-8 gap-1">
    //         <PlusCircle className="h-3.5 w-3.5" />
    //         <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
    //             Add Product
    //           </span>
    //       </Button>
    //     </div>
    //   </div>
    //   <TabsContent value="Reported Side Effects">
    //     Reported Side Effects
    //   </TabsContent>
    //   <TabsContent value="Aggregated Data">
    //     Aggregated Data
    //   </TabsContent>
    // </Tabs>
  // );
}
