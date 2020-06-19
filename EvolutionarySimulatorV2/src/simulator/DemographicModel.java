/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import event.HistoryOfEventsEvolutionarySimulator;
import exception.ExceptionDemography;
import exception.ExceptionPopulation;
import exception.ParameterException;
import parametersSimulation.ParametersGenome;
import parametersSimulation.parameters.ModelParameter;

/**
 *
 * @author Oscar Lao
 */
public abstract class DemographicModel {

    public DemographicModel(ParametersGenome pg) throws ParameterException, ExceptionPopulation, ExceptionDemography {
        this.pg = pg;
        defineDemography();
    }

    protected HistoryOfEventsEvolutionarySimulator events;
    protected final ParametersGenome pg;
    
    /**
     * Get the parameters of the genome
     * @return 
     */
    public ParametersGenome getPg()
    {
        return pg;
    }


    /**
     * Retrieve the parameterValue of the parameter name. It does not make any
     * attempt to know whether the parameter value is in the limits of the model
     *
     * @param parameterName
     * @return
     */
    public ModelParameter getParameterValue(String parameterName) {
        return events.getParametersInThisModel().get(events.getParametersInThisModel().indexOf(new ModelParameter(parameterName, null)));
    }

    /**
     * Get the name of the model. By default it is the name of the class that
     * has implemented the FastSimcoalModel.
     *
     * @return
     */
    public String modelName() {
        return getClass().getSimpleName();
    }

    /**
     * Get the demography so far
     *
     * @return
     */
    public HistoryOfEventsEvolutionarySimulator getDemography() {
        return events;
    }

    /**
     * Define the populations that are in the model
     *
     * @throws ParameterException
     */
    public abstract void defineDemography() throws ParameterException, ExceptionDemography, ExceptionPopulation;
}
