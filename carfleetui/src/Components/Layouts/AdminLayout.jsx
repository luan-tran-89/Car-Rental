import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";
import AdminPanelSettings from "@mui/icons-material/AdminPanelSettings";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import ElectricCarIcon from "@mui/icons-material/ElectricCar";
import DrawerNav from "../Navigation/DrawerNav";

const adminTabsData = [
  {
    label: "Managers",
    icon: <AdminPanelSettings />,
    route: "/admin/managers",
  },
  { label: "Customers", icon: <PeopleAltIcon />, route: "/admin/customers" },
  {
    label: "Car",
    icon: <ElectricCarIcon />,
    route: "/admin/cars",
  },
  {
    label: "Add Manager",
    icon: <AdminPanelSettings />,
    route: "/admin/add-manager",
  },
  {
    label: "Add Car",
    icon: <ElectricCarIcon />,
    route: "/admin/addcar",
  },
];

export default function AdminLayout() {
  return (
    <Box>
      <DrawerNav
        components={<Outlet />}
        tabsData={adminTabsData}
        title={"Welcome Admin"}
      />
    </Box>
  );
}
