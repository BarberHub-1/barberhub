export const AVAILABLE_SERVICES = [
  { id: "corte-cabelo", label: "Corte de Cabelo" },
  { id: "barba", label: "Barba" },
  { id: "sobrancelha", label: "Sobrancelha" },
  { id: "luzes", label: "Luzes" },
  { id: "descoloração", label: "Descoloração" },
  { id: "progressiva", label: "Progressiva" },
] as const;

export type ServiceId = typeof AVAILABLE_SERVICES[number]["id"];
export type ServiceLabel = typeof AVAILABLE_SERVICES[number]["label"];

export const getServiceLabel = (id: ServiceId): ServiceLabel => {
  const service = AVAILABLE_SERVICES.find(s => s.id === id);
  return service?.label || "";
}; 