// "use client";

// import { zodResolver } from "@hookform/resolvers/zod";
// import { useForm } from "react-hook-form";
// import * as z from "zod";
// import { Button } from "@/components/ui/button";
// import { Input } from "@/components/ui/input";
// import {
//   Card,
//   CardContent,
//   CardFooter,
//   CardHeader,
//   CardTitle,
// } from "@/components/ui/card";
// import { useToast } from "@/hooks/use-toast";
// import {
//   createAllergyBodySchema,
//     useCreateAllergyMutation
// } from "@/lib/apis/create-allergy.api";
// import { useRouter } from "next/navigation";
// import { useSuspenseQuery } from "@tanstack/react-query";
// import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
// import {
//   Form,
//   FormControl,
//   FormDescription,
//   FormField,
//   FormItem,
//   FormLabel,
//   FormMessage,
// } from "@/components/ui/form";
// import { UploadFileInput } from "@/components/upload-file-input";

// export function CreateAllergyForm() {
//   const { toast } = useToast();
//   const router = useRouter();
//   const currentUserInformationQuery = useSuspenseQuery(
//     createGetCurrentUserInformationQueryOptions(),
//   );

//   const createAllergyMutation = useCreateAllergyMutation();
//     const createAllergyForm = useForm<
//         z.infer<typeof createAllergyBodySchema>
//     >({
//         resolver: zodResolver(createAllergyBodySchema),
//         defaultValues: {
//             name: "My Allergy",
//             severity: "MILD",
//             reactionDescription: "",
//             userId: currentUserInformationQuery.data?.userId,
//         },
//     });

//     const onSubmit = createAllergyForm.handleSubmit(async (values) =>
//         createAllergyMutation.mutate(values, {
//             onSuccess() {
//                 toast({
//                     title: "Allergy created",
//                     description: "Your allergy has been created successfully",
//                 });
//                 router.push("/allergies");
//             },
//             onError() {
//                 toast({
//                     title: "Error creating allergy",
//                     description: "An error occurred while creating your allergy",
//                 });
//             },
//         }),
//     );
// }
//     return (
//         <Form onSubmit={onSubmit}>
//             <FormItem>
//                 <FormLabel htmlFor="name">Allergy Name</FormLabel>
//                 <FormField>
//                     <Input
//                         id="name"
//                         {...createAllergyForm.register("name")}
//                         placeholder="Enter allergy name"
//                     />
//                     <FormDescription>
//                         The name of the allergy you want to add
//                     </FormDescription>
//                     <FormMessage {...createAllergyForm.formState.errors.name} />
//                 </FormField>
//             </FormItem>

//             <FormItem>
//                 <FormLabel htmlFor="severity">Severity</FormLabel>
//                 <FormField>
//                     <Input
//                         id="severity"
//                         {...createAllergyForm.register("severity")}
//                         placeholder="Enter severity"
//                     />
//                     <FormDescription>
//                         The severity of the allergy you want to add
//                     </FormDescription>
//                     <FormMessage {...createAllergyForm.formState.errors.severity} />
//                 </FormField>
//             </FormItem>

//             <FormItem>
//                 <FormLabel htmlFor="reactionDescription">Reaction Description</FormLabel>
//                 <FormField>
//                     <Input
//                         id="reactionDescription"
//                         {...createAllergyForm.register("reactionDescription")}
//                         placeholder="Enter reaction description"
//                     />
//                     <FormDescription>
//                         The reaction description of the allergy you want to add
//                     </FormDescription>
//                     <FormMessage {...createAllergyForm.formState.errors.reactionDescription} />
//                 </FormField>
//             </FormItem>

//             <CardFooter>
//                 <Button type="submit">Create Allergy</Button>
//             </CardFooter>
//         </Form>
//     );

