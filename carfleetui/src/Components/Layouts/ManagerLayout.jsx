import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";
// import Header from "../Navigation/Header";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import ElectricCarIcon from "@mui/icons-material/ElectricCar";
import CommuteIcon from "@mui/icons-material/Commute";
import DrawerNav from "../Navigation/DrawerNav";

const managerTabsData = [
  {
    label: "Customers",
    icon: <PeopleAltIcon />,
    route: "/manager/customers",
  },
  { label: "Cars", icon: <CommuteIcon />, route: "/manager/cars" },
  // {
  //   label: "Add Customer",
  //   icon: <AddReactionIcon />,
  //   route: "/manager/addCustomer",
  // },
  // { label: "Add Car", icon: <ElectricCarIcon />, route: "/manager/addCar" },
];

export default function ManagerLayout() {
  return (
    <Box>
      <DrawerNav
        components={<Outlet />}
        tabsData={managerTabsData}
        title={"Welcome Manager"}
      />
    </Box>
  );
}
