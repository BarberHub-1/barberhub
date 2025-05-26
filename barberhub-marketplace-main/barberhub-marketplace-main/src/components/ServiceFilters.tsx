import { AVAILABLE_SERVICES, ServiceId } from "@/constants/services";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { ScrollArea } from "@/components/ui/scroll-area";

interface ServiceFiltersProps {
  selectedServices: ServiceId[];
  onServiceChange: (services: ServiceId[]) => void;
}

const getServiceLabel = (serviceId: ServiceId): string => {
  const service = AVAILABLE_SERVICES.find(s => s.id === serviceId);
  return service ? service.label : serviceId;
};

const ServiceFilters = ({ selectedServices, onServiceChange }: ServiceFiltersProps) => {
  const handleServiceChange = (serviceId: ServiceId, checked: boolean) => {
    if (checked) {
      onServiceChange([...selectedServices, serviceId]);
    } else {
      onServiceChange(selectedServices.filter(id => id !== serviceId));
    }
  };

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h3 className="text-lg font-semibold text-barber-900">Serviços</h3>
        {selectedServices.length > 0 && (
          <button
            onClick={() => onServiceChange([])}
            className="text-sm text-barber-600 hover:text-barber-900"
          >
            Limpar filtros
          </button>
        )}
      </div>
      
      <ScrollArea className="h-[300px] pr-4">
        <div className="space-y-3">
          {AVAILABLE_SERVICES.map((service) => (
            <div key={service.id} className="flex items-center space-x-2">
              <Checkbox
                id={service.id}
                checked={selectedServices.includes(service.id)}
                onCheckedChange={(checked) => 
                  handleServiceChange(service.id, checked as boolean)
                }
              />
              <Label
                htmlFor={service.id}
                className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
              >
                {service.label}
              </Label>
            </div>
          ))}
        </div>
      </ScrollArea>
    </div>
  );
};

export default ServiceFilters; 