import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import ElectricCarIcon from "@mui/icons-material/ElectricCar";
// import AddReactionIcon from "@mui/icons-material/AddReaction";
import AdminPanelSettingsIcon from "@mui/icons-material/AdminPanelSettings";
import Tabs from "../ResuableComponents/Tabs";

export function AdminTab() {
  const [activeTab, setActiveTab] = useState(0);
  const navigate = useNavigate();

  const tabsData = [
    {
      label: "Managers",
      icon: <AdminPanelSettingsIcon />,
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
      icon: <AdminPanelSettingsIcon />,
      route: "/admin/add-manager",
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
