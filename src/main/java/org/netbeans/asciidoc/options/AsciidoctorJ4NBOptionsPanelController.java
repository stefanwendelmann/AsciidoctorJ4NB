package org.netbeans.asciidoc.options;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

@OptionsPanelController.SubRegistration(
        displayName = "#AdvancedOption_DisplayName_AsciidoctorJ4NB",
        keywords = "#AdvancedOption_Keywords_AsciidoctorJ4NB",
        keywordsCategory = "Advanced/AsciidoctorJ4NB"
)
@org.openide.util.NbBundle.Messages(
{
  "AdvancedOption_DisplayName_AsciidoctorJ4NB=AsciidoctorJ4NB", "AdvancedOption_Keywords_AsciidoctorJ4NB=asciidoc, asciidoctor, ascii"
})
public final class AsciidoctorJ4NBOptionsPanelController extends OptionsPanelController
{

  private AsciidoctorJ4NBPanel panel;
  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  private boolean changed;

  public void update()
  {
    getPanel().load();
    changed = false;
  }

  public void applyChanges()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        getPanel().store();
        changed = false;
      }
    });
  }

  public void cancel()
  {
    // need not do anything special, if no changes have been persisted yet
  }

  public boolean isValid()
  {
    return getPanel().valid();
  }

  public boolean isChanged()
  {
    return changed;
  }

  public HelpCtx getHelpCtx()
  {
    return null; // new HelpCtx("...ID") if you have a help set
  }

  public JComponent getComponent(Lookup masterLookup)
  {
    return getPanel();
  }

  public void addPropertyChangeListener(PropertyChangeListener l)
  {
    pcs.addPropertyChangeListener(l);
  }

  public void removePropertyChangeListener(PropertyChangeListener l)
  {
    pcs.removePropertyChangeListener(l);
  }

  private AsciidoctorJ4NBPanel getPanel()
  {
    if (panel == null)
    {
      panel = new AsciidoctorJ4NBPanel(this);
    }
    return panel;
  }

  void changed()
  {
    if (!changed)
    {
      changed = true;
      pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
    }
    pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
  }

}
