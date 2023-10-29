import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import LocalCarWashIcon from "@mui/icons-material/LocalCarWash";
import HistoryIcon from "@mui/icons-material/History";
import CommuteIcon from "@mui/icons-material/Commute";
import PaidIcon from "@mui/icons-material/Paid";
import Tabs from "../ResuableComponents/Tabs";

export function CustomerTab() {
  const [activeTab, setActiveTab] = useState(0);
  const navigate = useNavigate();

  const tabsData = [
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

  const handleTabChange = (index) => {
    setActiveTab(index);
    navigate(tabsData[index].route);
  };

  return (
    <Tabs
      tabs={tabsData}
      activeTabIndex={activeTab}
      onTabChange={handleTabChange}
    />
  );
}
