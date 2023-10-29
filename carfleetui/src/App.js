import './App.css';
import Reservation from './Components/Customer/Reservation';
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom';
import RootLayout from './Components/Layouts/RootLayout';
import CarLayout from './Components/Layouts/CarLayout';
import { WelcomePage } from './Components/Welcome/WelcomePage';
import SignIn from './Components/SignInOut/SignIn';
import SignUp from './Components/SignInOut/SignUp';
import CustomerLayout from './Components/Layouts/CustomerLayout';
import AdminLayout from './Components/Layouts/AdminLayout';
import ManagerLayout from './Components/Layouts/ManagerLayout';
import CustomerViewCar from './Components/Customer/CustomerViewCar';
import ViewManagers from './Components/Manager/ViewManagers';
import ViewCustomers from './Components/Customer/ViewCustomers';
import AdminMangerViewCar from './Components/Manager/AdminMangerViewCar';
import AddCustomer from './Components/Customer/AddCustomer';
import { AddCar } from './Components/Car/AddCar';
import NotReserved from './Components/Customer/NotReserved';
import Accounts from './Components/Payment/Accounts';
import AddPayment from './Components/Payment/AddPayment';
import UpdatePayment from './Components/Payment/UpdatePayment';
import AddManager from './Components/Admin/AddManager';
import UpdateCustomer from './Components/Customer/UpdateCustomer';
import { UpdateCar } from './Components/Car/UpdateCar';
import CarMaintaince from './Components/Car/CarMaintaince';
import CarRentalHistory from './Components/Car/CarRentalHistory';
import CustomerRentalHistory from './Components/Customer/CustomerRentalHistory';
import UpdateManager from './Components/Manager/UpdateManager';
import ManagerCustomerLayout from './Components/Manager/ManagerCustomerLayout';
import ReserveACar from './Components/Customer/ReserveACar';
import {  Box } from '@mui/material';
import Payment from './Components/Payment/Payment';





const router = createBrowserRouter(
  createRoutesFromElements(

    <Route>
      <Route path="/" element={<RootLayout />}>

        {/* Welcome page route */}
        <Route index element={<WelcomePage />} />

        {/* login and register routes */}
        <Route path='login' element={<SignIn />} />
        <Route path='register' element={<SignUp />} />

        {/* amdin routers */}

        <Route path='admin' element={<AdminLayout />}>
          <Route path='managers' element={<ViewManagers />} />
          <Route path='customers' element={<ViewCustomers view={true} />} />
          <Route path='cars' element={<AdminMangerViewCar managerViewCar={false}/>}  />
          <Route path='customer/rentalhistory/:userId' element={<CustomerRentalHistory />} />
          <Route path='add-manager' element={<AddManager />} />
          <Route path='update-manager/:managerId' element={<UpdateManager />} />
          <Route path='addCar' element={<AddCar />} />
        </Route>

        {/* manager router */}
        <Route path='manager' element={<ManagerLayout />}>
          <Route path='customers' element={<ManagerCustomerLayout/>}>
          
          <Route index element={<ViewCustomers view={true} />} />
          <Route path='rentalhistory/:userId' element={<CustomerRentalHistory />} />
          <Route path='updatecustomer/:email' element={<UpdateCustomer />} />
          </Route>
          
          <Route path='cars' element={<CarLayout />} >
            <Route index element={<AdminMangerViewCar managerViewCar={true} />}/>
            <Route path='maintenanace/:carId' element={<CarMaintaince/>}/>
          </Route>
          
          <Route path='addCustomer' element={<AddCustomer />} />
          {/* <Route path='addCar' element={<AddCar />} /> */}
          
          <Route path='update-car' element={<UpdateCar />} />

        </Route>

        {/* Customer routers */}
        <Route path='customer' element={<CustomerLayout />}>
          <Route path='reservations' element={<Reservation />} />
          <Route path="payment" element={<Payment/>}/>
          <Route path='rentalhistory/:userId' element={<CustomerRentalHistory />} />
          <Route path='accounts' element={<Accounts />} />
          <Route path='cars' element={<CustomerViewCar />}  />
          <Route path='notreserved' element={<NotReserved />} />
          <Route path='accounts/add-card' element={<AddPayment />} />
          <Route path='accounts/update-payment/:cardId' element={<UpdatePayment />} />
          <Route path='reservecar/:carId' element={<ReserveACar/>}/>

          <Route
            path=":id"
          // element={}
          // loader={}
          />
        </Route>
      </Route>

      {/* car routes */}
      <Route path='car' element={<CarLayout />}>
        <Route path='maintainance' element={<CarMaintaince />} />
        <Route path='rental-history/:carId' element={<CarRentalHistory />} />
        
        <Route path='update-car/:carId' element={<UpdateCar />} />
        <Route path=':id' />
      </Route>
    </Route>


  )
)



function App() {
  return (

    <Box >
      

      <RouterProvider router={router}></RouterProvider>


    </Box>

  );
}

export default App;


/**************************************ROUTES*************************************************** */
// {/* <Header /> */ }
// {/* <MiniDrawer icons={adminIcons} userContainers={adminDrawerContainer} title="Admin Dashboard" /> */ }
// {/* <MiniDrawer icons={managerIcons} userContainers={managerDrawerContainer} title="Manager Dashboard" /> */ }
// {/* <MiniDrawer icons={customerIcons} userContainers={customerDrawerContainer} title="Customer Dashboard" /> */ }
// {/* <SignInSide /> */ }
// {/* <TestComponent /> */ }
// {/* <CustomizedDialogs /> */ }
// {/* <StackComponent /> */ }
// {/* <CardComponent /> */ }
// {/* <SignUp /> */ }
// {/* <SignUp /> */ }
// {/* <SignInUp /> */ }
// {/* <SignInSide /> */ }
// {/* <CarRegister /> */ }
// {/* <ViewCar /> */ }
// {/* <ListItems /> */ }
// {/* <SearchComponent /> */ }
// {/* <CustomerViewCar /> */ }
// {/* <AdminMangerViewCar /> */ }
// {/* <ListItems /> */ }
// {/* <ReusableListItem /> */ }
// {/* <Maintainace /> */ }
// {/* <RentalHistory /> */ }
// {/* <PaymentDetails /> */ }
// {/* <AddPayment /> */ }
// {/* <Reservation /> */ }
// {/* <DatePickerComponent /> */ }
// {/* <PickUp /> */ }


// import React, { useEffect, useState } from "react";
// import Button from "@mui/material/Button";
// import CssBaseline from "@mui/material/CssBaseline";
// import TextField from "@mui/material/TextField";
// import Grid from "@mui/material/Grid";
// import Box from "@mui/material/Box";
// import Container from "@mui/material/Container";
// import { createTheme, ThemeProvider } from "@mui/material/styles";
// import { useFormik } from "formik";
// import { getManager, getManagers, registerCustomer, updateManager } from "../../Actions/UserAction";
// import { useNavigate } from "react-router-dom";
// import { Typography } from "@mui/material";
// import {
//   CustomErrorDiv,
//   signUpValidationSchema,
// } from "../../Common/YupValidations";
// import * as Yup from "yup";
// import { useParams } from "react-router-dom";

// const defaultTheme = createTheme();

// export default function UpdateManager() {
//   const navigate = useNavigate();
//   const [manager, setManager] = useState({})
//   const { managerId } = useParams();
//   // console.log(typeof emailId)


//   useEffect(() => {
//     getManager(localStorage.getItem("userEmail"))
//       .then((res) => {
//         // Update form values with the API response
//         formik.setValues({
//           firstName: res.firstName || "",
//           lastName: res.lastName || "",
//           userName: res.userName || "",
//           phone: res.phone || "",
//           email: res.email || "",
//           password: res.password || "",
//           confirmpassword: res.confirmpassword || "",
//         });
//         setManager(res);
//       })
//       .catch((error) => {
//         console.log(error);
//       });
//   }, [localStorage.getItem("userEmail")]);

//   // useEffect(() => {
//   //   getManager(localStorage.getItem("userEmail"))
//   //     .then((res) => {
//   //       // Update form values with the API response
//   //       formik.setValues({
//   //         firstName: res.firstName || "",
//   //         lastName: res.lastName || "",
//   //         userName: res.userName || "",
//   //         phone: res.phone || "",
//   //         email: res.email || "",
//   //         password: res.password || "",
//   //         confirmpassword: res.confirmpassword || "",
//   //       });
//   //       setManager(res);
//   //     })
//   //     .catch((error) => {
//   //       console.error(error);
//   //     });
//   // }, [localStorage.getItem("userEmail")]);


//   const formik = useFormik({
//     initialValues: {
//       firstName: "",
//       lastName: "",
//       userName: "",
//       phone: "",
//       email: "",
//       password: "",
//       confirmpassword: "",
//     },
//     validationSchema: Yup.object(signUpValidationSchema),
//     onSubmit: (values, { resetForm }) => {
//       console.log(values);
//       // navigate("/admin");
//       const { confirmpassword, ...dataToSend } = values;

//       updateManager(managerId).then(res => {
//         console.log(res)
//       }).catch(error => {
//         console.log(error)
//         console.log("Error while updating the manager")
//       })

//     },
//   });


//   return (
//     <ThemeProvider theme={defaultTheme}>
//       <Container component="main" maxWidth="xs">
//         <CssBaseline />
//         <Box
//           sx={{
//             marginTop: 4,
//             display: "flex",
//             flexDirection: "column",
//             alignItems: "center",
//           }}
//         >
//           <Typography variant="h6" color={"error"}>
//             Update Manager
//           </Typography>
//           <Box
//             component="form"
//             noValidate
//             onSubmit={formik.handleSubmit}
//             sx={{ mt: 3 }}
//           >
//             <Grid container spacing={2}>
//               <Grid item xs={12} sm={6}>
//                 <TextField
//                   autoComplete="given-name"
//                   name="firstName"
//                   required
//                   fullWidth
//                   id="firstName"
//                   label="First Name"
//                   autoFocus
//                   value={formik.values.firstName}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.firstName && formik.errors.firstName ? (
//                   <CustomErrorDiv>{formik.errors.firstName}</CustomErrorDiv>
//                 ) : null}
//               </Grid>
//               <Grid item xs={12} sm={6}>
//                 <TextField
//                   required
//                   fullWidth
//                   id="lastName"
//                   label="Last Name"
//                   name="lastName"
//                   autoComplete="family-name"
//                   value={formik.values.lastName}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.lastName && formik.errors.lastName ? (
//                   <CustomErrorDiv>{formik.errors.lastName}</CustomErrorDiv>
//                 ) : null}
//               </Grid>
//               <Grid item xs={12} sm={6}>
//                 <TextField
//                   required
//                   fullWidth
//                   id="username"
//                   label="Username"
//                   name="userName"
//                   autoComplete="family-name"
//                   value={formik.values.userName}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.userName && formik.errors.userName ? (
//                   <CustomErrorDiv>{formik.errors.userName}</CustomErrorDiv>
//                 ) : null}
//               </Grid>
//               <Grid item xs={12} sm={6}>
//                 <TextField
//                   required
//                   fullWidth
//                   id="phone"
//                   label="Phone"
//                   name="phone"
//                   autoComplete="family-name"
//                   value={formik.values.phone}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.phone && formik.errors.phone ? (
//                   <CustomErrorDiv>{formik.errors.phone}</CustomErrorDiv>
//                 ) : null}
//               </Grid>
//               <Grid item xs={12}>
//                 <TextField
//                   required
//                   fullWidth
//                   id="email"
//                   label="Email Address"
//                   name="email"
//                   autoComplete="email"
//                   value={formik.values.email}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.email && formik.errors.email ? (
//                   <CustomErrorDiv>{formik.errors.email}</CustomErrorDiv>
//                 ) : null}
//               </Grid>
//               <Grid item xs={12}>
//                 <TextField
//                   required
//                   fullWidth
//                   name="password"
//                   label="Password"
//                   type="password"
//                   id="password"
//                   autoComplete="new-password"
//                   value={formik.values.password}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.password && formik.errors.password ? (
//                   <CustomErrorDiv>{formik.errors.password}</CustomErrorDiv>
//                 ) : null}
//               </Grid>
//               <Grid item xs={12}>
//                 <TextField
//                   required
//                   fullWidth
//                   name="confirmpassword"
//                   label="Confirm Password"
//                   type="password"
//                   id="confirmpassword"
//                   autoComplete="new-password"
//                   value={formik.values.confirmpassword}
//                   onChange={formik.handleChange}
//                   onBlur={formik.handleBlur}
//                 />
//                 {formik.touched.confirmpassword &&
//                   formik.errors.confirmpassword ? (
//                   <CustomErrorDiv>
//                     {formik.errors.confirmpassword}
//                   </CustomErrorDiv>
//                 ) : null}
//               </Grid>
//             </Grid>
//             <Button
//               type="submit"
//               fullWidth
//               variant="outlined"
//               sx={{ mt: 3, mb: 2 }}
//               onClick={() => navigate("/admin/managers")}
//             >
//               Update Manager
//             </Button>
//           </Box>
//         </Box>
//       </Container>
//     </ThemeProvider>
//   );
// }
