import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";
import LocalCarWashIcon from "@mui/icons-material/LocalCarWash";
import HistoryIcon from "@mui/icons-material/History";
import CommuteIcon from "@mui/icons-material/Commute";
import PaidIcon from "@mui/icons-material/Paid";
import DrawerNav from "../Navigation/DrawerNav";

const customerTabsData = [
  {
    label: "Cars",
    icon: <LocalCarWashIcon />,
    route: "/customer/cars",
  },
  {
    label: "Rental History",
    icon: <HistoryIcon />,
    route: "/customer/rentalhistory",
  },
  {
    label: "Reservations",
    icon: <CommuteIcon />,
    route: "/customer/reservations",
  },
  {
    label: "Accounts",
    icon: <PaidIcon />,
    route: "/customer/accounts",
  },
];

export default function CustomerLayout() {
  return (
    <Box>
      <DrawerNav
        components={<Outlet />}
        tabsData={customerTabsData}
        title={"Welcome Customer"}
      />
    </Box>
  );
}
